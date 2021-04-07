package com.aldredo.qrcode.presentation.activity

import com.aldredo.qrcode.data.model.stateRequest.MenuItem

interface AuthorizationView {
    fun openFoyerActivity(menu: ArrayList<MenuItem>)

    fun showMessageError(message: String)
}