package com.aldredo.qrcode.di.component

import android.app.Application
import com.aldredo.core.base.di.CoreComponent
import com.aldredo.core.base.network.SwitchServer
import com.aldredo.core.base.room.AppDatabase
import com.aldredo.core.base.util.IManagerInfoDevice
import com.aldredo.qrcode.di.AppScope
import dagger.Component
import retrofit2.Retrofit

@AppScope
@Component(dependencies = [CoreComponent::class])
interface AppComponent {

    fun provideRetrofit(): Retrofit

    fun provideRoom(): AppDatabase

    fun provideManagerToken(): IManagerInfoDevice

    fun provideSwitchToken(): SwitchServer

    companion object {
        fun create(context: Application) =
            DaggerAppComponent.builder()
                .coreComponent(CoreComponent.create(context))
                .build()!!

    }
}