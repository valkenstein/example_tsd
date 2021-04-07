package com.aldredo.qrcode.data.repository

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import com.aldredo.qrcode.data.api.UpdateAppApi
import com.aldredo.qrcode.data.model.stateRequest.ResponseStateUpdateApp
import retrofit2.Retrofit
import javax.inject.Inject

class UpdateRepository @Inject constructor(
    private val retrofit: Retrofit,
    private val manager: DownloadManager
) {
    fun updateVersion(url: String): String {
        return try {
            DownloadManager.Request(Uri.parse(url)).apply {
                setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                setTitle("Download")
                setDescription("Downloading apk...")
                allowScanningByMediaScanner()
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "" + System.currentTimeMillis()
                )
                manager.enqueue(this)
            }
            "идет загрузка"
        } catch (e: Exception) {
            e.message.toString()
        }
    }

    suspend fun checkLastVersion(): ResponseStateUpdateApp {
        return try {
            val result = retrofit.create(UpdateAppApi::class.java).checkLastVersion()
            ResponseStateUpdateApp.Result(result)
        } catch (e: Exception) {
            ResponseStateUpdateApp.Error(e.message)
        }
    }
}