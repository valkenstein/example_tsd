package com.aldredo.api_tsd

import android.content.Context
import com.aldredo.api_droid.demo.SwitcherScanDroid
import com.aldredo.api_lite.SwitcherScanLite
import com.aldredo.api_orovo2.SwitcherScanOrovo
import com.aldredo.api_slim.SwitcherScanSlim
import com.aldredo.core.base.barcode.BroadCodeListener
import com.aldredo.core.base.barcode.ISwitcherScan

object FactorySwitcher {
    fun searchSwitcher(
        typeOS: String,
        context: Context,
        broadCodeReceiver: BroadCodeListener
    ): ISwitcherScan {
        return when (typeOS) {
            "ATOL Smart.Lite" ->
                SwitcherScanLite(context, broadCodeReceiver)
            "ATOL Smart.Slim" ->
                SwitcherScanSlim(context, broadCodeReceiver)
            "U2" ->
                SwitcherScanOrovo(context, broadCodeReceiver)
            else ->
                SwitcherScanDroid(context, broadCodeReceiver)
        }
    }
}