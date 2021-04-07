package com.aldredo.qrcode.presentation.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aldredo.qrcode.data.model.CellsResponse
import com.aldredo.qrcode.data.model.NomenclatureModel
import com.aldredo.qrcode.data.model.stateRequest.ResponseStateCells
import com.aldredo.qrcode.data.model.stateRequest.ResponseStateNomenclature
import com.aldredo.qrcode.data.repository.CellsBdRepository
import com.aldredo.qrcode.data.repository.CellsRepository
import com.aldredo.qrcode.data.repository.NomenclatureBDRepository
import com.aldredo.qrcode.data.repository.NomenclatureRepository
import com.aldredo.qrcode.di.TaskWindowScope
import kotlinx.coroutines.*
import javax.inject.Inject

@TaskWindowScope
class TaskWindowViewModel @Inject constructor(
    private val nomenclatureRepository: NomenclatureRepository,
    private val nomenclatureBDRepository: NomenclatureBDRepository,
    private val cellsRepository: CellsRepository,
    private val cellsBdRepository: CellsBdRepository,
) { private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val errorMessages: MutableLiveData<String> = MutableLiveData()
    fun getErrorMessages(): LiveData<String> = errorMessages

    private val statusMessage: MutableLiveData<String> = MutableLiveData()
    fun getStatusMessage(): LiveData<String> = statusMessage

    private val progressBar: MutableLiveData<Boolean> = MutableLiveData()
    fun getProgressBar(): LiveData<Boolean> = progressBar

    private val nomenclatures: MutableLiveData<ArrayList<NomenclatureModel>> = MutableLiveData()
    fun getNomenclatures(): LiveData<ArrayList<NomenclatureModel>> = nomenclatures



    private suspend fun getCells() = withContext(Dispatchers.IO) {
        cellsBdRepository.getCells()
    }

    private suspend fun saveCellsToBDAsync(result: ArrayList<CellsResponse.CellsModel>) =
        withContext(Dispatchers.IO) {
            cellsBdRepository.saveCellsToBd(result)
        }

    private suspend fun saveNomenclatureToBDAsync(result: ArrayList<NomenclatureModel>) =
        withContext(Dispatchers.IO) {
            nomenclatureBDRepository.saveNomenclatureToBD(result)
        }

    private suspend fun getListNomenclatureAsync() = scope.async(Dispatchers.IO) {
        nomenclatureRepository.getListNomenclature()
    }

    private fun getListCellsAsync() = scope.async(Dispatchers.IO) {
        cellsRepository.getListCells()
    }
}