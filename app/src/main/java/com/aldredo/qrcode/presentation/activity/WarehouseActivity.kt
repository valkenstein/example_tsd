package com.aldredo.qrcode.presentation.activity

import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.aldredo.core.base.barcode.Barcode
import com.aldredo.core.base.barcode.BroadCodeListener
import com.aldredo.core.base.barcode.ISwitcherScan
import com.aldredo.core.base.mvvm.ModelFactory
import com.aldredo.qrcode.R
import com.aldredo.qrcode.di.component.ActivityComponent
import com.aldredo.qrcode.presentation.presenter.WarehouseViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.integration.android.IntentIntegrator
import javax.inject.Inject

class WarehouseActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: WarehouseViewModel
    @Inject
    lateinit var switcherScan: ISwitcherScan
    @Inject
    lateinit var broadCast: BroadCodeListener

    private val integrator = IntentIntegrator(this).apply {
        setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
        setPrompt("Scan a barcode")
        setCameraId(0) // Use a specific camera of the device
        setBeepEnabled(false)
        setBarcodeImageEnabled(true)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warehouse)
        ActivityComponent.create(this)?.inject(this)
        viewModel = ViewModelProviders.of(this, ModelFactory(viewModel)).get(viewModel::class.java)
        broadCast.addSubscribers(viewModel)

        viewModel.getCellsData().observe(this, {
            findViewById<TextView>(R.id.storage_address).text = it
        })
        viewModel.getPalletData().observe(this, {
            findViewById<TextView>(R.id.goods_pallet).text = it
        })
        viewModel.getProgressBar().observe(this, {
            findViewById<ProgressBar>(R.id.progress_bar).visibility =
                if (it) View.VISIBLE else View.GONE
        })
        viewModel.getMessageError().observe(this, {
            toast(it)
        })

        viewModel.getStatusMessage().observe(this, {
            Snackbar.make(findViewById<ProgressBar>(R.id.progress_bar), it, 2500).show()
        })
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun putOnAddress(view: View) {
        viewModel.putCells()
    }
    override fun onResume() {
        super.onResume()
        switcherScan.enableScan()
    }

    override fun onPause() {
        super.onPause()
        switcherScan.disableScan()
    }
    fun showToDisplay(view: View) {
        scanCode()
    }

    private fun scanCode() {
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val resultScanCamera = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        resultScanCamera?.apply {
            if (resultScanCamera.contents != null) {
                broadCast.scanResultFire(Barcode("", resultScanCamera.contents))
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}