package com.aldredo.qrcode.di

import android.content.Context
import android.os.Build
import com.aldredo.core.base.barcode.BroadCodeListener
import com.aldredo.api_tsd.FactorySwitcher
import com.aldredo.core.base.barcode.ISwitcherScan
import dagger.Module
import dagger.Provides

@Module
class SwitcherScannerModule {
    @Provides
    fun provideSwitcherScanner(
        context: Context,
        broadCodeReceiver: BroadCodeListener
    ): ISwitcherScan {
        return FactorySwitcher.searchSwitcher(Build.MODEL, context, broadCodeReceiver)
    }
}
