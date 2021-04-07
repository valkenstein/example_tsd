package com.aldredo.core.base.barcode

import android.content.BroadcastReceiver

abstract class BaseBroadcastReceiver(val broadCodeListener: BroadCodeListener) :
    BroadcastReceiver() {
}