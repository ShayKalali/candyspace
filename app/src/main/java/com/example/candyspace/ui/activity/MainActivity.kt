package com.example.candyspace.ui.activity

import android.os.Bundle
import com.example.candyspace.R
import com.example.candyspace.logic.base.BaseActivity
import com.example.candyspace.logic.base.BaseFragment
import com.example.candyspace.ui.fragment.userlist.UserListFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment(UserListFragment::class.java)
    }
    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.main_activity_frame) as BaseFragment<*, *>
        fragment.onBackPressed { super.onBackPressed() }
    }
}