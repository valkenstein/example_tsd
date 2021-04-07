package com.aldredo.core.base.interceptor

import com.aldredo.core.base.util.IManagerInfoDevice
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiInterceptor @Inject constructor(
    private val managerInfoDevice: IManagerInfoDevice
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            return chain.proceed(createRequest(chain))
        }
    }

    private fun createRequest(chain: Interceptor.Chain) =
        chain.request().run {
            val urlWithApiKey = url
                .newBuilder()
                .build()

            newBuilder()
                //.header("Authorization", "Bearer ${managerInfoDevice.getInfoDevice()}")
                .header("hidden", "hidden")
                .header("hidden", "hidden")
                .header("hidden", "hidden")
                .header("hidden", "hidden")
                .url(urlWithApiKey)
                .build()
        }
}
