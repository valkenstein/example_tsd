package com.aldredo.core.base.util

import com.aldredo.core.base.network.BodyRequest

internal class ManagerInfoDevice(private val bodyRequest: BodyRequest) : IManagerInfoDevice {
    private var managerInfoDevice = InfoDevice()
    override fun getInfoDevice() = managerInfoDevice

    override fun getBodyRequest(): BodyRequest {
        return bodyRequest
    }

    override fun saveInfoDevice(infoDevice: InfoDevice) {
        this.managerInfoDevice = infoDevice
        bodyRequest.device_info = infoDevice
    }
}