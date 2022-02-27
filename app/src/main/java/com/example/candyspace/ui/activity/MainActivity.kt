package com.example.candyspace.ui.activity

import android.os.Bundle
import com.example.candyspace.R
import com.example.candyspace.logic.base.BaseActivity
import com.example.candyspace.logic.base.BaseFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.main_activity_frame) as BaseFragment<*, *>
        fragment.onBackPressed { super.onBackPressed() }
    }
}