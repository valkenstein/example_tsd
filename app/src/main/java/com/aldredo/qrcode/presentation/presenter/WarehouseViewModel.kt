package com.aldredo.qrcode.presentation.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aldredo.core.base.barcode.Barcode
import com.aldredo.qrcode.data.model.CellsResponse
import com.aldredo.qrcode.data.model.stateRequest.ResponseStateBarcodeSearch
import com.aldredo.qrcode.data.model.stateRequest.ResponseStateCells
import com.aldredo.qrcode.data.repository.BarcodeTypeRepository
import com.aldredo.qrcode.data.repository.CellsBdRepository
import com.aldredo.qrcode.data.repository.CellsRepository
import com.aldredo.core.base.barcode.BroadCastListener
import kotlinx.coroutines.*
import javax.inject.Inject

class WarehouseViewModel @Inject constructor(
    private val cellsBdRepository: CellsBdRepository,
    private val barcodeTypeRepository: BarcodeTypeRepository,
    private val cellsRepository: CellsRepository
) : ViewModel(), BroadCastListener {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val cellsData = MutableLiveData<String>()
    private val palletData = MutableLiveData<String>()
    private val progressBar = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()
    private val statusMessage = MutableLiveData<String>()

    fun getCellsData() = cellsData
    fun getPalletData() = palletData
    fun getProgressBar() = progressBar
    fun getMessageError() = errorMessage
    fun getStatusMessage() = statusMessage

    init {
        scope.launch {
            progressBar.postValue(true)
            statusMessage.postValue("загрузка с сервера")
            verifyResponseCells(getListCellsAsync().await())
            progressBar.postValue(false)
        }
    }

    private suspend fun verifyResponseCells(resultCells: ResponseStateCells) {
        when (resultCells) {
            is ResponseStateCells.Result -> {
                statusMessage.postValue("кэширование ячеек в бд")
                val messageCellsBd = saveCellsToBDAsync(resultCells.result.cells)
                statusMessage.postValue(messageCellsBd)
            }
            is ResponseStateCells.Error -> {
                errorMessage.postValue("Cells - ${resultCells.message}")
            }
            is ResponseStateCells.EmptyResponse -> {
                errorMessage.postValue("Cells - данные пусты")
            }
        }
    }


    override fun onBroadCode(barcode: Barcode) {
        scope.launch {
            val cells = searchBarcodeToBdAsync(barcode.value.toString())
            if (cells != null) {
                verifyTypeBarcode(cells.type, cells.name)
            } else {
                searchBarcodeToServer(barcode)
            }
        }
    }

    fun putCells() = scope.launch {
        when {
            cellsData.value?.isNotEmpty() == true && palletData.value?.isNotEmpty() == true -> {
                progressBar.postValue(true)
                verifyResponseToServerPutCells(putCellsAsync(cellsData.value!!, palletData.value!!))
                progressBar.postValue(false)
            }
            cellsData.value.isNullOrEmpty() -> {
                errorMessage.postValue("сканируйте штрихкод ячейки")
            }
            palletData.value.isNullOrEmpty() -> {
                errorMessage.postValue("сканируйте штрихкод товара")
            }
        }
    }

    private fun verifyResponseToServerPutCells(responseStateCells: ResponseStateCells) {
        when (responseStateCells) {
            is ResponseStateCells.Result -> {
                cellsData.postValue("")
                palletData.postValue("")
                errorMessage.postValue("выполнено")
            }
            is ResponseStateCells.Error -> {
                errorMessage.postValue(responseStateCells.message)
            }
            else -> {
                errorMessage.postValue("данные пусты")
            }
        }
    }

    private suspend fun searchBarcodeToServer(barcode: Barcode) {
        when (val result = searchBarcodeToServerAsync(barcode.value ?: "")) {
            is ResponseStateBarcodeSearch.Result -> {
                verifyTypeBarcode(
                    result.result.cell?.type ?: "",
                    result.result.cell?.name ?: ""
                )
            }
            is ResponseStateBarcodeSearch.Error -> {
                errorMessage.postValue(result.message)
            }
        }
    }

    private fun verifyTypeBarcode(type: String, name: String) {
        when (type) {
            "1" -> {
                cellsData.postValue(name)
            }
            "2" -> {
                palletData.postValue(name)
            }
            else -> {
                errorMessage.postValue("ошибка типа, тип - $type название - $name")
            }
        }
    }

    private suspend fun putCellsAsync(cellsValue: String, palletValue: String) =
        withContext(Dispatchers.IO) {
            cellsRepository.putGoodsToCell(cellsValue, palletValue)
        }

    private suspend fun searchBarcodeToServerAsync(barcode: String) = withContext(Dispatchers.IO) {
        barcodeTypeRepository.verifyTypeBarcode(barcode)
    }

    private fun getListCellsAsync() = scope.async(Dispatchers.IO) {
        cellsRepository.getListCells()
    }

    private suspend fun saveCellsToBDAsync(result: ArrayList<CellsResponse.CellsModel>) =
        withContext(Dispatchers.IO) {
            cellsBdRepository.saveCellsToBd(result)
        }

    private suspend fun searchBarcodeToBdAsync(search: String) = withContext(Dispatchers.IO) {
        cellsBdRepository.searchBarcodeToBd(search)
    }
}