package com.aldredo.core.base.di

import android.app.Application
import com.aldredo.core.base.network.SwitchServer
import com.aldredo.core.base.room.AppDatabase
import com.aldredo.core.base.util.IManagerInfoDevice
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        OkHttpClientModule::class,
        ManagerModule::class,
        DataBaseRoom::class]
)
interface CoreComponent {
    fun provideRetrofit(): Retrofit

    fun provideRoom(): AppDatabase

    fun provideManagerToken(): IManagerInfoDevice

    fun provideSwitchServer(): SwitchServer

    companion object {
        fun create(context: Application) =
            DaggerCoreComponent
                .builder()
                .setActivity(context)
                .build()
    }

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun setActivity(context: Application): Builder

        fun build(): CoreComponent
    }
}


