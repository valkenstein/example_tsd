package com.aldredo.core.base.navigation.mediator

import android.os.Bundle
import androidx.fragment.app.Fragment

interface IAuthoMediator {
    fun openCodeScreen(fragment: Fragment, bundle: Bundle)
    companion object{
        const val ARG_NEWS = "newsAll"
        const val ARG_POSITION = "position"
    }
}