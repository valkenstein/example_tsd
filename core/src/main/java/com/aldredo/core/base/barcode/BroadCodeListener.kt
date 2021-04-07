package com.aldredo.core.base.barcode

interface BroadCodeListener {
    fun scanResultFire(barcode: Barcode)
    fun addSubscribers(subscriber: BroadCastListener)
    fun removeSubscriber()
}