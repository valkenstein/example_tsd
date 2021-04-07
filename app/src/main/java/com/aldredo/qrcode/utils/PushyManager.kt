package com.aldredo.qrcode.utils

import android.content.Context
import com.aldredo.core.base.util.IManagerInfoDevice
import com.aldredo.qrcode.data.repository.RegisterForPushNotificationsAsync
import kotlinx.coroutines.*
import me.pushy.sdk.Pushy
import javax.inject.Inject

class PushyManager @Inject constructor(val activity: Context) {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    @Inject
    lateinit var managerToken: IManagerInfoDevice

    init {
        Pushy.listen(activity)
    }

    fun loadPushToken() = scope.launch(Dispatchers.Main) {
        try {
            asyncLoadPushToken()
        } catch (e: Exception) {
            "инета нет"
        }
    }

    private suspend fun asyncLoadPushToken() = withContext(Dispatchers.IO) {
        Pushy.register(activity.applicationContext).apply {
            if (Pushy.isRegistered(activity)) {
                val deviceToken = Pushy.register(activity)
                RegisterForPushNotificationsAsync(deviceToken, managerToken).execute()
            }
        }
    }

    fun cancel() {
        scope.coroutineContext.cancelChildren()
    }
}