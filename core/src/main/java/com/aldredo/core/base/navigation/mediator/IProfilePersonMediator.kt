package com.aldredo.core.base.navigation.mediator

import android.os.Bundle
import androidx.fragment.app.Fragment

interface IProfilePersonMediator {
    fun openEditPerson(fragment: Fragment, bundle: Bundle)

    fun openAuthorization(fragment: Fragment, bundle: Bundle)
}