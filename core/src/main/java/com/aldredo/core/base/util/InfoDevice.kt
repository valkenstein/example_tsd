package com.aldredo.core.base.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.TELEPHONY_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.SERIAL
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

data class InfoDevice(
    var model: String = "",
    var device: String = "",
    var id: String = "",
    var host: String = "",
    var serial: String = "",
    var IMEI: String = "",
    var device_id: String = "",
    var notification_id: String = ""
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getPermission(activity: Activity): Boolean {
        val permissionCheck =
            ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                1
            )
        return ContextCompat.checkSelfPermission(
            activity, Manifest.permission.READ_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("HardwareIds")
    fun init(activity: Activity): InfoDevice {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            if (getPermission(activity)) {
                try {
//                    serial =
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//                            Build.getSerial() else SERIAL
                } finally {
                }
           //     IMEI = (activity.getSystemService(TELEPHONY_SERVICE) as TelephonyManager).deviceId
            }
        model = Build.MODEL
        device = Build.DEVICE
        id = Build.ID
        host = Build.HOST
        device_id = Settings.Secure.getString(
            activity.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        return this
    }

}