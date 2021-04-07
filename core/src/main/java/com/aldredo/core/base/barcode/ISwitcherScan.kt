package com.aldredo.core.base.barcode

import android.content.Context

abstract class ISwitcherScan(context: Context) {
    abstract fun enableScan()
    abstract fun disableScan()
}