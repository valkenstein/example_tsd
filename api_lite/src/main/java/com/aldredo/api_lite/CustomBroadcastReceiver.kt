package com.aldredo.api_lite

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.aldredo.core.base.barcode.Barcode
import com.aldredo.core.base.barcode.BaseBroadcastReceiver
import com.aldredo.core.base.barcode.BroadCodeListener

class CustomBroadcastReceiver(broadCodeListener: BroadCodeListener) : BaseBroadcastReceiver(broadCodeListener) {

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra("EXTRA_BARCODE_DECODING_SYMBOLE")
        val barcode = intent.getStringExtra("EXTRA_BARCODE_DECODING_DATA")

        broadCodeListener.scanResultFire(Barcode(type, barcode))
    }
}
