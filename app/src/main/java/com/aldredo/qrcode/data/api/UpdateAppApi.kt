package com.aldredo.qrcode.data.api

import com.aldredo.qrcode.data.model.UpdateVersionModel
import retrofit2.http.POST
import retrofit2.http.Url

interface UpdateAppApi {
    @POST
    suspend fun checkLastVersion(@Url appPath: String = URL): UpdateVersionModel

    @POST
    suspend fun downloadApk(@Url fileUrl: String)

    companion object {
        private const val URL = "hidden"
    }
}