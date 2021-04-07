package com.aldredo.api_droid.demo

import android.content.Context
import android.content.Intent
import com.aldredo.api_droid.emdk.datawedge
import com.aldredo.core.base.barcode.Barcode
import com.aldredo.core.base.barcode.BaseBroadcastReceiver
import com.aldredo.core.base.barcode.BroadCodeListener

class BarcodeDataBroadcastReceiver(broadCodeListener: BroadCodeListener) : BaseBroadcastReceiver(
    broadCodeListener
) {
    override fun onReceive(arg0: Context, arg1: Intent) {
        val barcode = arg1.getStringExtra(datawedge.DATA_STRING)
        val type = arg1.getIntExtra(datawedge.DATA_TYPE, 0)

        broadCodeListener.scanResultFire(Barcode(type.toString(), barcode))
    }
}