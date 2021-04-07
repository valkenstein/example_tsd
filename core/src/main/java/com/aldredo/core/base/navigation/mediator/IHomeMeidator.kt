package com.aldredo.core.base.navigation.mediator

import android.os.Bundle
import androidx.fragment.app.Fragment

interface IHomeMeidator {
    fun openNewsCard(fragment: Fragment, bundle: Bundle)

    fun openActionCard(fragment: Fragment, bundle: Bundle)
}