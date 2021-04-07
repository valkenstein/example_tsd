package com.aldredo.qrcode.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.aldredo.core.base.barcode.Barcode
import com.aldredo.core.base.barcode.ISwitcherScan
import com.aldredo.core.base.util.IManagerInfoDevice
import com.aldredo.qrcode.R
import com.aldredo.qrcode.data.model.stateRequest.MenuItem
import com.aldredo.qrcode.di.component.ActivityComponent
import com.aldredo.qrcode.presentation.presenter.TaskWindowViewModel
import com.aldredo.core.base.barcode.BroadCastListener
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable
import javax.inject.Inject

class MenuActivity : AppCompatActivity(), BroadCastListener {
    @Inject
    lateinit var taskWindowViewModel: TaskWindowViewModel

    @Inject
    lateinit var switcherScan: ISwitcherScan

    @Inject
    lateinit var manager: IManagerInfoDevice
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        ActivityComponent.create(this)?.inject(this)
        initView(intent.getSerializableExtra(PARAM_MENU))
        //taskWindowViewModel.init()
        bindViewModel()
    }

    override fun onBroadCode(barcode: Barcode) {
        print("")
    }

    private fun bindViewModel() {
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        taskWindowViewModel.getProgressBar().observe(this, {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
            enableClick(!it)
        })

        taskWindowViewModel.getStatusMessage().observe(this, {
            Snackbar.make(progressBar, it, 2500).show()
        })

        taskWindowViewModel.getErrorMessages().observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun enableClick(it: Boolean) {
        findViewById<ViewGroup>(R.id.body).apply {
            for (i in 0 until childCount)
                getChildAt(i).isClickable = it
        }
    }

    private fun initView(listMenu: Serializable?) {
        findViewById<Button>(R.id.exit).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.move_to_cell).setOnClickListener {
            startActivity(Intent(this, MoveToCellActivity::class.java))
        }

        findViewById<Button>(R.id.sclad).setOnClickListener {
            startActivity(Intent(this, WarehouseActivity::class.java))
        }

        findViewById<ViewGroup>(R.id.body).apply {
            for (item in listMenu as ArrayList<MenuItem>)
                addView(Button(this@MenuActivity).apply {
                    text = item.name
                    setOnClickListener {
                        verifyType(item.type, item.params.storage_id)
                    }
                })
        }
    }

    private fun verifyType(type: String, storageId: String) {
        when (type) {
            "MoveToCell" -> {
                startActivity(
                    Intent(
                        this@MenuActivity,
                        MoveToCellActivity::class.java
                    ).apply {
                        putExtra("storage", storageId)
                        manager.getBodyRequest()?.storage_id = storageId
                    }
                )
            }
            "ReceivingOnStorage" -> {
                startActivity(Intent(
                    this@MenuActivity,
                    WarehouseActivity::class.java
                ).apply {
                    putExtra("storage", storageId)
                    manager.getBodyRequest()?.storage_id = storageId
                })
            }
            "" -> {
                openTaskActivity("item")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        switcherScan.enableScan()
    }

    override fun onPause() {
        super.onPause()
        switcherScan.disableScan()
    }

    private fun openTaskActivity(param: String) {
        startActivity(Intent(this, TaskWindow::class.java).apply {
            putExtra(TaskWindow.PARAM, param)
        })
    }

    companion object {
        const val PARAM_MENU = "MENU"
    }
}