package com.aldredo.qrcode.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.aldredo.qrcode.R
import com.aldredo.qrcode.presentation.adapter.PageAdapter
import com.google.android.material.tabs.TabLayout

class TaskWindow : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_window)
        val tab = findViewById<TabLayout>(R.id.tabLayout)
        findViewById<ViewPager>(R.id.view_pager).apply {
            adapter = PageAdapter(supportFragmentManager, tab.tabCount)
            tab.setupWithViewPager(this)
        }
        intent.getStringArrayExtra(PARAM)
    }

    companion object {
        const val PARAM = "PARAM"
    }
}