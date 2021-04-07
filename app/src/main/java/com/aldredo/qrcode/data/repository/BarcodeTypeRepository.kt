package com.aldredo.qrcode.data.repository

import com.aldredo.core.base.util.IManagerInfoDevice
import com.aldredo.qrcode.data.api.BarcodeTypeApi
import com.aldredo.qrcode.data.model.stateRequest.ResponseStateBarcodeSearch
import com.aldredo.qrcode.data.model.stateRequest.ResponseStateCells
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject

class BarcodeTypeRepository @Inject constructor(
    private val retrofit: Retrofit,
    private val manager: IManagerInfoDevice
) {
    suspend fun verifyTypeBarcode(barcode: String): ResponseStateBarcodeSearch {
        return try {
            val cells = retrofit.create(BarcodeTypeApi::class.java).verifyBarcode()
            if (cells.error)
                return ResponseStateBarcodeSearch.Error(cells.error_text)

            ResponseStateBarcodeSearch.Result(cells)
        } catch (e: Exception) {
            ResponseStateBarcodeSearch.Error(e.message)
        }
    }
}