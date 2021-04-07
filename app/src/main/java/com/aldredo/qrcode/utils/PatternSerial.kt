package com.aldredo.qrcode.utils

class PatternSerial {
    companion object {
        private val listSerial = listOf(
            "2*********",
            "GK******",
        )

        fun isSerial(serial: String): Boolean {
            listSerial.forEach { itemSerial ->
                val prefItem = getPref(itemSerial)
                val pref = getPref(serial, prefItem.length)
                if (serial.length == itemSerial.length && prefItem == pref) {
                    return true
                }
            }
            return false
        }

        private fun getPref(serial: String) = serial.substringBefore("*")
        private fun getPref(serial: String, length: Int) = serial.substring(0, length)
    }
}