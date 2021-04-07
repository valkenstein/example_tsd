package com.aldredo.qrcode.data.model.stateRequest

import java.io.Serializable

data class ResponseAuthorizationModel(val token: String, val menu: ArrayList<MenuItem>)
data class MenuItem(
    val name: String,
    val type: String,
    var params: Param
) : Serializable

data class Param(val storage_id: String) : Serializable