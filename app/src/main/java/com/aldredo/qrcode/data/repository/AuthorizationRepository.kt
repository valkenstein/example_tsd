package com.aldredo.qrcode.data.repository

import com.aldredo.core.base.util.IManagerInfoDevice
import com.aldredo.qrcode.data.api.AuthorizationApi
import com.aldredo.qrcode.data.model.AuthorizationRequestModel
import com.aldredo.qrcode.data.model.stateRequest.ResponseStateAuthorization
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject

class AuthorizationRepository @Inject constructor(
    private val retrofit: Retrofit,
    private val manager: IManagerInfoDevice
) {
    suspend fun tokenValidation(login: String) = try {
        val message = retrofit.create(AuthorizationApi::class.java)
            .checkingAuthorizationData(AuthorizationRequestModel(login, manager.getInfoDevice()))
        manager.getBodyRequest()?.token = message!!.token
        ResponseStateAuthorization.Result(message)
    } catch (e: Exception) {
        print(e.message)
        ResponseStateAuthorization.Error(e.message)
    }
}