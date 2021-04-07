package com.aldredo.qrcode.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aldredo.core.base.barcode.Barcode
import com.aldredo.core.base.barcode.BroadCodeListener
import com.aldredo.core.base.barcode.ISwitcherScan
import com.aldredo.core.base.mvvm.ModelFactory
import com.aldredo.qrcode.R
import com.aldredo.qrcode.di.component.ActivityComponent
import com.aldredo.qrcode.presentation.adapter.MoveToCellAdapter
import com.aldredo.qrcode.presentation.presenter.MoveToCellViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.integration.android.IntentIntegrator
import javax.inject.Inject

class MoveToCellActivity : AppCompatActivity() {
    @Inject
    lateinit var broadCast: BroadCodeListener

    @Inject
    lateinit var switcherScan: ISwitcherScan

    private val integrator = IntentIntegrator(this).apply {
        setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
        setPrompt("Scan a barcode")
        setCameraId(0) // Use a specific camera of the device
        setBeepEnabled(false)
        setBarcodeImageEnabled(true)
    }
    private lateinit var cellView: TextView

    lateinit var moveToCellAdapter: MoveToCellAdapter

    @Inject
    lateinit var viewModel: MoveToCellViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_to_cell)
        ActivityComponent.create(this)?.inject(this)
        viewModel = ViewModelProviders.of(this, ModelFactory(viewModel)).get(viewModel::class.java)
        moveToCellAdapter = MoveToCellAdapter(viewModel)
        broadCast.addSubscribers(viewModel)

        cellView = findViewById(R.id.cell_view)
        findViewById<RecyclerView>(R.id.recycler_view).apply {
            adapter = moveToCellAdapter
            layoutManager = LinearLayoutManager(this@MoveToCellActivity)
        }

        viewModel.getCellsData().observe(this, {
            cellView.text = it
        })

        viewModel.getMessageError().observe(this, {
            toast(it)
        })

        viewModel.getStatusMessage().observe(this, {
            Snackbar.make(cellView, it, 2500).show()
        })

        viewModel.getProgressBar().observe(this, {
            findViewById<ProgressBar>(R.id.progress_bar).visibility =
                if (it) View.VISIBLE else View.GONE
        })

        viewModel.getSerialData().observe(this, {
            moveToCellAdapter.submitList(it)
            moveToCellAdapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        switcherScan.enableScan()
    }

    override fun onPause() {
        super.onPause()
        switcherScan.disableScan()
    }

    fun putOnAddress(view: View) {
        viewModel.putCells()
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun scanCode() {
        integrator.initiateScan()
    }

    fun run(view: View) {
        scanCode()
    }

    fun showToDisplay(view: View) {
        viewModel.showToDisplaySeries()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val r = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        r?.apply {
            if (r.contents != null) {
                broadCast.scanResultFire(Barcode(type = "xz", contents))
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}