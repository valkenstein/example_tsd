package com.aldredo.api_slim

import android.content.Context
import android.content.IntentFilter
import com.aldredo.core.base.barcode.BroadCodeListener
import com.aldredo.core.base.barcode.ISwitcherScan

class SwitcherScanSlim(var context: Context, listener: BroadCodeListener) : ISwitcherScan(context) {
    private val customBroadcastReceiver = SlimBroadCastReceiver(listener)

    override fun enableScan() {
        context.registerReceiver(
            customBroadcastReceiver,
            IntentFilter(
                ACTION_OPEN_SCAN_BROADCAST
            )
        )
    }

    override fun disableScan() {
        context.unregisterReceiver(customBroadcastReceiver)
    }

    companion object {
        const val ACTION_OPEN_SCAN_BROADCAST = "com.xcheng.scanner.action.BARCODE_DECODING_BROADCAST"
    }
}