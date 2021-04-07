package com.aldredo.core.base.di

import com.aldredo.core.base.network.BodyRequest
import com.aldredo.core.base.util.IManagerInfoDevice
import com.aldredo.core.base.util.ManagerInfoDevice
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ManagerModule {
    @Provides
    @Singleton
    internal fun provideManagerToken(bodyRequest: BodyRequest): IManagerInfoDevice {
        return ManagerInfoDevice(bodyRequest)
    }

    @Provides
    @Singleton
    internal fun provideBodyRequest(): BodyRequest {
        return BodyRequest()
    }
}