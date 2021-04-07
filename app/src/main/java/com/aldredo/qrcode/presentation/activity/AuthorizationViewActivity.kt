package com.aldredo.qrcode.presentation.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.aldredo.core.base.network.SwitchServer
import com.aldredo.core.base.util.IManagerInfoDevice
import com.aldredo.qrcode.R
import com.aldredo.qrcode.data.model.stateRequest.MenuItem
import com.aldredo.qrcode.di.component.ActivityComponent
import com.aldredo.qrcode.presentation.presenter.AuthorizationPresenter
import com.aldredo.qrcode.utils.PushyManager
import com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE
import retrofit2.Retrofit
import javax.inject.Inject

class AuthorizationViewActivity : AppCompatActivity(), AuthorizationView {
    @Inject
    lateinit var pushy: PushyManager

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var presenter: AuthorizationPresenter

    @Inject
    lateinit var switchServer: SwitchServer

    @Inject
    lateinit var managerInfo: IManagerInfoDevice
    var counter = -99

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
        ActivityComponent.create(this)?.inject(this)
        getVersionCode()
        managerInfo.getInfoDevice()?.init(this)
        pushy.loadPushToken()
        findViewById<Button>(R.id.button).setOnClickListener {
            presenter.tokenValidation(findViewById<EditText>(R.id.input).text.toString())
        }
        findViewById<Switch>(R.id.switch_demo).setOnCheckedChangeListener { buttonView, isChecked ->
            if (counter > FLAG) {
                if (isChecked) {
                    showMessageError("поздравляю вы стали разработчиком")
                    switchHost(true)
                } else {
                    switchHost(false)
                    counter = -99
                }
            } else {
                buttonView.isChecked = false
            }
        }
        findViewById<TextView>(R.id.version).setOnClickListener {
            counter++
        }
        findViewById<TextView>(R.id.version).setOnLongClickListener {
            counter = 0
            true
        }

        findViewById<ImageView>(R.id.loader).setOnClickListener {
            presenter.checkUpdateVersionApp(getVersionCode())
        }
        requestPermission()
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CODE
                )
            }
        }
    }

    private fun switchHost(combatServer: Boolean) {
        if (combatServer) {
            switchServer.enableCombatServer()
        } else {
            switchServer.enableTestServer()
        }
        ActivityComponent.restart(this)
        ActivityComponent.create(this)?.inject(this)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == 24) {
            print("левая кнопка нажата urovo u2")
            return false
        }
        if (keyCode == 25) {
            print("правая кнопка нажата urovo u2")
            return false
        }

        return super.onKeyUp(keyCode, event)
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
        pushy.cancel()
    }


    override fun openFoyerActivity(menu: ArrayList<MenuItem>) {
        startActivity(Intent(this, MenuActivity::class.java).apply {
            putExtra(MenuActivity.PARAM_MENU, menu)
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        managerInfo.getInfoDevice()?.init(this)
    }

    private fun getVersionCode(): Int {
        try {
            packageManager.getPackageInfo(packageName, 0).apply {
                findViewById<TextView>(R.id.version).text = getString(R.string.version, versionName)
                return versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return 0
    }

    override fun showMessageError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val FLAG = 3
    }
}