package com.aldredo.core.base.navigation.mediator

import com.aldredo.core.base.navigation.mediator.IAuthoMediator
import com.aldredo.core.base.navigation.mediator.IHomeMeidator

interface ProvideMediator {
    fun getAuthoMediator(): IAuthoMediator

    fun getHomeMediator(): IHomeMeidator

    fun getProfilePerson(): IAuthoCodeMediator

     fun getProfileMediator(): IProfilePersonMediator
}