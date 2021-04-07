package com.aldredo.qrcode.application

import android.app.Application
import com.aldredo.qrcode.di.component.AppComponent

class App : Application(), ApplicationApi {
    override fun onCreate() {
        super.onCreate()
        createAppComponent()
    }

    private fun createAppComponent(): AppComponent {
        return appComponent ?: AppComponent.create(this).apply {
            appComponent = this
        }
    }

    override fun restartComponent() {
        appComponent = AppComponent.create(this)
    }

    companion object {
        var appComponent: AppComponent? = null
    }
}