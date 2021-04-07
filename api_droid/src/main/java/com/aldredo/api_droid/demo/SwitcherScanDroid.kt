package com.aldredo.api_droid.demo

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.View
import android.widget.CheckBox
import com.aldredo.api_droid.emdk.datawedge
import com.aldredo.core.base.barcode.BroadCodeListener
import com.aldredo.core.base.barcode.ISwitcherScan

class SwitcherScanDroid(var context: Context, listener: BroadCodeListener): ISwitcherScan(context) {
    private var beepMode = true // decode beep enable
    private var vibrateMode = true // decode vibrate enable

    //Counter
    private var decodes = 0

    //Listen to decoded result
    private val intentBarcodeDataReceiver = BarcodeDataBroadcastReceiver(listener)

    override fun enableScan() {
        DisableJanamScanner()
    }

    override fun disableScan() {
        context.registerReceiver(intentBarcodeDataReceiver, IntentFilter("DATA_SCAN"))
        EnableJanamScanner()
    }

    // Beep Setting
    var mCheckBeepListener = View.OnClickListener { v ->
        beepMode = (v as CheckBox).isChecked
        val BeepSoundIntent = Intent()
        BeepSoundIntent.action = datawedge.SOFTSCANTRIGGER
        if (beepMode) BeepSoundIntent.putExtra(
            datawedge.EXTRA_PARAMETER,
            datawedge.ENABLE_BEEP
        ) else BeepSoundIntent.putExtra(datawedge.EXTRA_PARAMETER, datawedge.DISABLE_BEEP)
        context.sendBroadcast(BeepSoundIntent)
    }

    // Vibrate Setting
    var mCheckVibrateListener = View.OnClickListener { v ->
        vibrateMode = (v as CheckBox).isChecked
        val VibrateIntent = Intent()
        VibrateIntent.action = datawedge.SOFTSCANTRIGGER
        if (vibrateMode) VibrateIntent.putExtra(
            datawedge.EXTRA_PARAMETER,
            datawedge.ENABLE_VIBRATE
        ) else VibrateIntent.putExtra(datawedge.EXTRA_PARAMETER, datawedge.DISABLE_VIBRATE)
        context.sendBroadcast(VibrateIntent)
    }

    // Decode
    var mDecodeListener = View.OnClickListener { StartScanner() }

    // Decoder Setting
    var mCodeSettingListener = View.OnClickListener {
        // val myIntent = Intent(this@SDLguiActivity, CodeSetting::class.java)
        // this@SDLguiActivity.startActivity(myIntent)
    }

    // Default Setting
    var mDefaultListener = View.OnClickListener {
        val i = Intent()
        i.action = datawedge.SCANNERINPUTPLUGIN
        i.putExtra(datawedge.EXTRA_PARAMETER, datawedge.SET_DEFAULT_SETTING)
        context.sendBroadcast(i)
    }

    fun StartScanner() {
        val scannerIntent = Intent()
        scannerIntent.action = datawedge.SOFTSCANTRIGGER
        scannerIntent.putExtra(datawedge.EXTRA_PARAMETER, datawedge.START_SCANNING)
        context.sendBroadcast(scannerIntent)
    }

    fun StopScanner() {
        val scannerIntent = Intent()
        scannerIntent.action = datawedge.SOFTSCANTRIGGER
        scannerIntent.putExtra(datawedge.EXTRA_PARAMETER, datawedge.STOP_SCANNING)
        context.sendBroadcast(scannerIntent)
    }

    fun DisableJanamScanner() {
        val TriggerButtonIntent = Intent()
        TriggerButtonIntent.action = datawedge.SOFTSCANTRIGGER
        TriggerButtonIntent.putExtra(datawedge.EXTRA_PARAMETER, datawedge.DISABLE_TRIGGERBUTTON)
        context.sendBroadcast(TriggerButtonIntent)
        val BeepSoundIntent = Intent()
        BeepSoundIntent.action = datawedge.SOFTSCANTRIGGER
        BeepSoundIntent.putExtra(datawedge.EXTRA_PARAMETER, datawedge.DISABLE_BEEP)
        context.sendBroadcast(BeepSoundIntent)
        val i = Intent()
        i.action = datawedge.SCANNERINPUTPLUGIN
        i.putExtra(datawedge.EXTRA_PARAMETER, "DISABLE_PLUGIN")
        context.sendBroadcast(i)
    }

    fun EnableJanamScanner() {
        val i = Intent()
        i.action = datawedge.SCANNERINPUTPLUGIN
        i.putExtra(datawedge.EXTRA_PARAMETER, "ENABLE_PLUGIN")
        context.sendBroadcast(i)
        val TriggerButtonIntent = Intent()
        TriggerButtonIntent.action = datawedge.SOFTSCANTRIGGER
        TriggerButtonIntent.putExtra(datawedge.EXTRA_PARAMETER, datawedge.ENABLE_TRIGGERBUTTON)
        context.sendBroadcast(TriggerButtonIntent)
        val BeepSoundIntent = Intent()
        BeepSoundIntent.action = datawedge.SOFTSCANTRIGGER
        BeepSoundIntent.putExtra(datawedge.EXTRA_PARAMETER, datawedge.ENABLE_BEEP)
        context.sendBroadcast(BeepSoundIntent)
        val VibrateIntent = Intent()
        VibrateIntent.action = datawedge.SOFTSCANTRIGGER
        VibrateIntent.putExtra(datawedge.EXTRA_PARAMETER, datawedge.ENABLE_VIBRATE)
        context.sendBroadcast(VibrateIntent)

        //Intent CodeIntent = new Intent();
        //CodeIntent.setAction(SCANNERINPUTPLUGIN);
        //CodeIntent.putExtra(EXTRA_PARAMETER, ENABLE_EAN13);
        //CodeIntent.putExtra(EXTRA_PARAMETER, DISABLE_EAN13);
        // app.sendBroadcast(CodeIntent);
    }
}
