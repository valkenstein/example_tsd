package com.aldredo.core.base.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

interface IBottomNavManager {
    fun init(
        fragmentManager: FragmentManager,
        containerId: Int,
        bottomNavigationView: BottomNavigationView
    )

    fun setupNavController()

    fun onSaveInstanceState(outState: Bundle?)

    fun onRestoreInstanceState(savedInstanceState: Bundle?)

    fun onBackPressed(): Boolean
}