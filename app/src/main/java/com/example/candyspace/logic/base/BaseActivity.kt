package com.example.candyspace.logic.base

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.candyspace.R
import com.example.candyspace.logic.util.SharedViewModel
import dagger.android.support.DaggerAppCompatActivity


abstract class BaseActivity : DaggerAppCompatActivity() {
    fun <T : BaseFragment<*, *>> openFragment(type: Class<T>) {
        val fragment = type.newInstance()
        if (!supportFragmentManager.isDestroyed) {
            supportFragmentManager.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )

                add(R.id.main_activity_frame, fragment as Fragment)

                addToBackStack(null)
                commitAllowingStateLoss()
            }
        }
    }

    fun getSharedViewModel(): SharedViewModel {
        val viewModel: SharedViewModel by viewModels()
        return viewModel
    }

}