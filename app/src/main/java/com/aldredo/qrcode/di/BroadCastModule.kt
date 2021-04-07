package com.aldredo.qrcode.di

import com.aldredo.core.base.barcode.BroadCodeListener
import com.aldredo.qrcode.utils.BroadCodeReceiver
import dagger.Binds
import dagger.Module

@Module
interface BroadCastModule {
    @TaskWindowScope
    @Binds
    fun provideBroad(broad: BroadCodeReceiver): BroadCodeListener
}
