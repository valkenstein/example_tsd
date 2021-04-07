package com.aldredo.qrcode.presentation.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aldredo.core.base.barcode.Barcode
import com.aldredo.qrcode.data.model.CellsResponse
import com.aldredo.qrcode.data.model.NomenclatureModel
import com.aldredo.qrcode.data.model.SerialModel
import com.aldredo.qrcode.data.model.stateRequest.*
import com.aldredo.qrcode.data.repository.*
import com.aldredo.core.base.barcode.BroadCastListener
import com.aldredo.qrcode.utils.PatternSerial
import kotlinx.coroutines.*
import javax.inject.Inject

class MoveToCellViewModel @Inject constructor(
    private val nomenclatureBDRepository: NomenclatureBDRepository,
    private val moveToCellRepository: MoveToCellRepository,
    private val cellsBdRepository: CellsBdRepository,
    private val cellsRepository: CellsRepository,
    private val nomenclatureRepository: NomenclatureRepository,
) : ViewModel(),
    BroadCastListener {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val cellsData = MutableLiveData<String>()
    private val serialData = MutableLiveData<ArrayList<SerialModel>>()
    private val progressBar = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()
    private val statusMessage = MutableLiveData<String>()
    private val seriesAdapter = ArrayList<SerialModel>()

    fun getCellsData() = cellsData
    fun getSerialData() = serialData
    fun getProgressBar() = progressBar
    fun getMessageError() = errorMessage
    fun getStatusMessage() = statusMessage

    init {
        scope.launch {
            progressBar.postValue(true)
            statusMessage.postValue("загрузка с сервера")
            val nomenclature = getListNomenclatureAsync()
            val cells = getListCellsAsync()
            val resultNomenclature = nomenclature.await()
            val resultCells = cells.await()
            verifyNomenclature(resultNomenclature)
            verifyCells(resultCells)
        }
    }


    private suspend fun verifyCells(resultCells: ResponseStateCells) {
        when (resultCells) {
            is ResponseStateCells.Result -> {
                statusMessage.postValue("кэширование ячеек в бд")
                val messageCellsBd = saveCellsToBDAsync(resultCells.result.cells)
                statusMessage.postValue(messageCellsBd)
                progressBar.postValue(false)
            }
            is ResponseStateCells.EmptyResponse -> {
                errorMessage.postValue("Cells - пустые данные")
                progressBar.postValue(false)
            }
            is ResponseStateCells.Error -> {
                errorMessage.postValue("Cells - ${resultCells.message}")
                progressBar.postValue(false)
            }
        }
    }

    private suspend fun verifyNomenclature(resultNomenclature: ResponseStateNomenclature) {
        when (resultNomenclature) {
            is ResponseStateNomenclature.Result -> {
                statusMessage.postValue("кэширование номенклатуры в бд")
                val messageNomenclatureBd = saveNomenclatureToBDAsync(resultNomenclature.result)
                statusMessage.postValue(messageNomenclatureBd)
                progressBar.postValue(false)
            }
            is ResponseStateNomenclature.EmptyResponse -> {
                errorMessage.postValue("сервер для Nomenclature вернул пустой ответ")
                progressBar.postValue(false)
            }
            is ResponseStateNomenclature.Error -> {
                errorMessage.postValue("Nomenclature - произошла  ошибка на сервере ${resultNomenclature.message}")
                progressBar.postValue(false)
            }
        }
    }

    override fun onBroadCode(barcode: Barcode) {
        scope.launch {
            barcode.value?.let {
                if (PatternSerial.isSerial(it)) {
                    checkSerialToBd(it)
                } else {
                    if (!checkCells(barcode.value!!)) {
                        errorMessage.postValue("такого штрих кода мы не знаем")
                    }
                }
            }
        }
    }

    private suspend fun checkSerialToBd(barcodeValue: String) {
        val seriesToBd = searchSeriesToBdAsync(barcodeValue)
        if (seriesToBd != null) {
            val name = searchNameToBdAsync(seriesToBd.id_nomenclature)
            addElementGoods(barcodeValue, name ?: "-")
        } else {
            addElementGoods(barcodeValue, "-")
        }
    }

    private fun addElementGoods(barcode: String, name: String) {
        if (seriesAdapter.contains(SerialModel(barcode, name))) {
            errorMessage.postValue("такой элемент уже существует")
        } else {
            seriesAdapter.add(SerialModel(series = barcode, name))
            serialData.postValue(seriesAdapter)
        }
    }

    fun deleteElement(serialModel: SerialModel) {
        seriesAdapter.remove(serialModel)
        serialData.postValue(seriesAdapter)
    }

    private suspend fun searchSeriesToBdAsync(barcode: String) = withContext(Dispatchers.IO) {
        nomenclatureBDRepository.searchSerialToBd(barcode)
    }

    private suspend fun searchNameToBdAsync(id: String) = withContext(Dispatchers.IO) {
        nomenclatureBDRepository.getNameForSeries(id)
    }

    private suspend fun checkCells(barcode: String) =
        searchBarcodeToBdAsync(barcode)?.let {
            verifyTypeBarcode(it.type, it.name)
        } != null


    fun putCells() = scope.launch {
        when {
            cellsData.value?.isNotEmpty() == true && serialData.value?.isNotEmpty() == true -> {
                progressBar.postValue(true)
                verifyResponseToServerPutCells(putCellsAsync(cellsData.value!!, serialData.value!!))
                progressBar.postValue(false)
            }
            cellsData.value.isNullOrEmpty() -> {
                errorMessage.postValue("сканируйте штрихкод ячейки")
            }
            serialData.value.isNullOrEmpty() -> {
                errorMessage.postValue("сканируйте штрихкод товара")
            }
        }
    }

    fun showToDisplaySeries() = scope.launch {
        if (seriesAdapter.size > 0) {
            progressBar.postValue(true)
            getSeriesForServer()
            progressBar.postValue(false)
        } else {
            errorMessage.postValue("нет серийников для отображения")
        }
    }

    private fun getSeriesForServer() = scope.launch {
        when (val result = getListNomenclatureAsync(seriesAdapter)) {
            is ResponseStateNomenclatureSeries.Result -> {
                seriesAdapter.clear()
                seriesAdapter.addAll(result.result)
                serialData.postValue(seriesAdapter)
            }
            is ResponseStateNomenclatureSeries.Error -> {
                errorMessage.postValue(result.message)
            }
            is ResponseStateNomenclatureSeries.EmptyResponse -> {
                errorMessage.postValue("пустые данные")
            }
        }
    }

    private fun verifyResponseToServerPutCells(responseStateCells: ResponseStateMoveToCell) {
        when (responseStateCells) {
            is ResponseStateMoveToCell.Result -> {
                cellsData.postValue("")
                serialData.postValue(ArrayList()) to
                        errorMessage.postValue("выполнено")
            }
            is ResponseStateMoveToCell.Error -> {
                errorMessage.postValue(responseStateCells.message)
            }
            else -> {
                errorMessage.postValue("нет данных")
            }
        }
    }

    private fun verifyTypeBarcode(type: String, name: String) {
        when (type) {
            "1" -> {
                cellsData.postValue(name)
            }
            "2" -> {
                cellsData.postValue(name)
            }
            else -> {
                errorMessage.postValue("ошибка типа, тип - $type название - $name")
            }
        }
    }

    private suspend fun saveCellsToBDAsync(result: ArrayList<CellsResponse.CellsModel>) =
        withContext(Dispatchers.IO) {
            cellsBdRepository.saveCellsToBd(result)
        }

    private suspend fun putCellsAsync(cellsValue: String, series: List<SerialModel>) =
        withContext(Dispatchers.IO) {
            moveToCellRepository.putSerialAndCells(cellsValue, series)
        }


    private suspend fun searchBarcodeToBdAsync(search: String) = withContext(Dispatchers.IO) {
        cellsBdRepository.searchBarcodeToBd(search)
    }

    private fun getListCellsAsync() = scope.async(Dispatchers.IO) {
        cellsRepository.getListCells()
    }

    private suspend fun getListNomenclatureAsync() = scope.async(Dispatchers.IO) {
        nomenclatureRepository.getListNomenclature()
    }

    private suspend fun getListNomenclatureAsync(list: List<SerialModel>) =
        withContext(Dispatchers.IO) {
            nomenclatureRepository.getListNomenclature(list)
        }

    private suspend fun saveNomenclatureToBDAsync(result: ArrayList<NomenclatureModel>) =
        withContext(Dispatchers.IO) {
            nomenclatureBDRepository.saveNomenclatureToBD(result)
        }
}