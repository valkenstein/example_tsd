package com.aldredo.qrcode.presentation.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.aldredo.qrcode.presentation.activity.tabFragment.BaseFragment
import com.aldredo.qrcode.presentation.activity.tabFragment.UnloadingFragment
import com.aldredo.qrcode.presentation.activity.tabFragment.CollectedFragment

class PageAdapter(
    fm: FragmentManager,
    private val numOfTabs: Int
) : FragmentPagerAdapter(fm, numOfTabs) {
    override fun getCount(): Int {
        return numOfTabs
    }

    override fun getItem(position: Int) = getPositionFragment(position)

    private fun getPositionFragment(position: Int): BaseFragment {
        return when (position) {
            0 -> UnloadingFragment.newInstance()
            1 -> CollectedFragment.newInstance()
            else -> null!!
        }
    }

    override fun getItemPosition(`object`: Any) = POSITION_NONE

    override fun getPageTitle(position: Int) =
        getPositionFragment(position).nameFragment
}