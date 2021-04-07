package com.aldredo.core.base.network

import com.aldredo.core.base.util.InfoDevice
import javax.inject.Inject

data class BodyRequest(
    var token: String = "",
    var device_info: InfoDevice = InfoDevice(),
    var cell: String = "",
    var barcode: String = "",
    var storage_id: String = "451e2c38-50f3-11e7-8100-003048c130e1",
    var series: ArrayList<String> = arrayListOf()
) {
    data class Storage(val storage: ArrayList<String> = arrayListOf())
}