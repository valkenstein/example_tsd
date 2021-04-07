package com.aldredo.api_orovo2

import android.content.Context
import android.content.Intent
import android.device.ScanManager
import android.util.Log
import com.aldredo.core.base.barcode.Barcode
import com.aldredo.core.base.barcode.BaseBroadcastReceiver
import com.aldredo.core.base.barcode.BroadCodeListener

class OrovoBroadcastReceiver(broadCodeListener: BroadCodeListener) :
    BaseBroadcastReceiver(broadCodeListener) {
    override fun onReceive(p0: Context?, intent: Intent) {

        val barcode: ByteArray? = intent.getByteArrayExtra(ScanManager.DECODE_DATA_TAG)
        val barcodelen: Int = intent.getIntExtra(ScanManager.BARCODE_LENGTH_TAG, 0)
        val type: Byte = intent.getByteExtra(ScanManager.BARCODE_TYPE_TAG, 0.toByte())
        Log.i("debug", "----codetype--$type")
        val barcodeStr = String(barcode!!, 0, barcodelen)

        broadCodeListener.scanResultFire(Barcode(type.toString(), barcodeStr))
    }
}