package com.aldredo.api_slim

import android.content.Context
import android.content.Intent
import com.aldredo.core.base.barcode.Barcode
import com.aldredo.core.base.barcode.BaseBroadcastReceiver
import com.aldredo.core.base.barcode.BroadCodeListener

class SlimBroadCastReceiver(broadCodeListener: BroadCodeListener) :
    BaseBroadcastReceiver(broadCodeListener) {
    override fun onReceive(context: Context?, intent: Intent) {
        val barcode = intent.getStringExtra("EXTRA_BARCODE_DECODING_DATA")
        val symbology = intent.getStringExtra("EXTRA_BARCODE_DECODING_SYMBOLE")

        broadCodeListener.scanResultFire(Barcode(symbology, barcode))
    }
}