package com.aldredo.qrcode.utils

import com.aldredo.core.base.barcode.Barcode
import com.aldredo.core.base.barcode.BroadCastListener
import com.aldredo.core.base.barcode.BroadCodeListener
import javax.inject.Inject

class BroadCodeReceiver @Inject constructor() : BroadCodeListener {
    var subscriber: BroadCastListener? = null
    override fun scanResultFire(barcode: Barcode) {
        barcode.value = barcode.value?.trim()
        subscriber?.onBroadCode(barcode)
    }

    override fun addSubscribers(subscriber: BroadCastListener) {
        val h = hashCode()
        this.subscriber = subscriber
    }

    override fun removeSubscriber() {
        subscriber = null
    }
}