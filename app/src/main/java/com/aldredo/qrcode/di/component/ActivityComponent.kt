package com.aldredo.qrcode.di.component

import android.content.Context
import com.aldredo.qrcode.application.App
import com.aldredo.qrcode.application.ApplicationApi
import com.aldredo.qrcode.di.BroadCastModule
import com.aldredo.qrcode.di.DownloadManagerModule
import com.aldredo.qrcode.di.SwitcherScannerModule
import com.aldredo.qrcode.di.TaskWindowScope
import com.aldredo.qrcode.presentation.activity.AuthorizationViewActivity
import com.aldredo.qrcode.presentation.activity.MenuActivity
import com.aldredo.qrcode.presentation.activity.MoveToCellActivity
import com.aldredo.qrcode.presentation.activity.WarehouseActivity
import com.aldredo.qrcode.presentation.activity.tabFragment.UnloadingFragment
import dagger.BindsInstance
import dagger.Component

@TaskWindowScope
@Component(
    modules = [SwitcherScannerModule::class, BroadCastModule::class, DownloadManagerModule::class],
    dependencies = [AppComponent::class]
)
interface ActivityComponent {
    fun inject(app: UnloadingFragment)

    fun inject(app: WarehouseActivity)

    fun inject(app: MoveToCellActivity)

    fun inject(app: MenuActivity)

    fun inject(app: AuthorizationViewActivity)

    companion object {
        fun create(context: Context) =
            App.appComponent?.let {
                DaggerActivityComponent
                    .builder()
                    .setActivity(context)
                    .setAppComponent(it)
                    .build()
            }

        fun restart(context: Context) {
            (context.applicationContext as ApplicationApi).restartComponent()
        }
    }

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun setActivity(context: Context): Builder

        fun setAppComponent(app: AppComponent): Builder

        fun build(): ActivityComponent
    }
}