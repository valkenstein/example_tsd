package com.aldredo.core.base.network

import com.aldredo.core.base.di.OkHttpClientModule

class SwitchServer {
    fun enableTestServer() {
        OkHttpClientModule.switchTestServer()
    }

    fun enableCombatServer() {
        OkHttpClientModule.switchProductionServer()
    }
}