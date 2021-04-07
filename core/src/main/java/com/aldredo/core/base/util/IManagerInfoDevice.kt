package com.aldredo.core.base.util

import com.aldredo.core.base.network.BodyRequest

interface IManagerInfoDevice {
    fun getInfoDevice(): InfoDevice?
    fun getBodyRequest(): BodyRequest?
    fun saveInfoDevice(infoDevice: InfoDevice)
}