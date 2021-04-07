package com.aldredo.api_orovo2

import android.content.Context
import android.content.IntentFilter
import android.device.ScanManager
import com.aldredo.core.base.barcode.BroadCodeListener
import com.aldredo.core.base.barcode.ISwitcherScan


class SwitcherScanOrovo(var context: Context, listener: BroadCodeListener) :
    ISwitcherScan(context) {
    private val customBroadcastReceiver = OrovoBroadcastReceiver(listener)

    override fun enableScan() {
        context.registerReceiver(
            customBroadcastReceiver,
            IntentFilter(
                ScanManager.ACTION_DECODE
            )
        )
    }

    override fun disableScan() {
        context.unregisterReceiver(customBroadcastReceiver)
    }
}