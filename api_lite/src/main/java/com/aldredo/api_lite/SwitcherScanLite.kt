package com.aldredo.api_lite

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.aldredo.core.base.barcode.BroadCodeListener
import com.aldredo.core.base.barcode.ISwitcherScan

class SwitcherScanLite(var context: Context, listener: BroadCodeListener) : ISwitcherScan(context) {
    private val customBroadcastReceiver = CustomBroadcastReceiver(listener)
    private fun setTriggerStates(enabled: Boolean) {
        for (key in TRIGGER_KEYS) {
            val intent = Intent()
            intent.action = ACTION_CONTROL_SCANKEY
            intent.putExtra(EXTRA_SCANKEY_CODE, key)
            intent.putExtra(EXTRA_SCANKEY_STATUS, enabled)
            context.sendBroadcast(intent)
        }
    }

    override fun enableScan() {
        context.registerReceiver(
            customBroadcastReceiver,
            IntentFilter(API_PACKED_NAME)
        )
        setTriggerStates(true)
    }

    override fun disableScan() {
        setTriggerStates(false)
        context.unregisterReceiver(customBroadcastReceiver)
    }

    companion object {
        const val ACTION_CONTROL_SCANKEY = "com.xcheng.scanner.action.ACTION_CONTROL_SCANKEY"
        const val API_PACKED_NAME = "com.xcheng.scanner.action.BARCODE_DECODING_BROADCAST"
        const val CLOSE_SCAN = "com.xcheng.scanner.action.ACTION_CLOSE_SCAN"
        const val EXTRA_SCANKEY_STATUS = "extra_scankey_STATUS"
        const val EXTRA_SCANKEY_CODE = "extra_scankey_code"
        const val SCANKEY = "scankey"
        const val KEY_SCAN = 301
        const val KEY_LEFT = 80
        const val KEY_RIGHT = 27
        var TRIGGER_KEYS = intArrayOf(KEY_SCAN, KEY_LEFT, KEY_RIGHT)
    }
}