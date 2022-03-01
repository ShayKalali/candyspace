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

    fun setUpActionBar(
        isBackButtonVisible: Boolean,
        title: String = ""
    ) {
        supportActionBar?.customView?.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.black
            )
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(isBackButtonVisible)
        setActionBarTitle(title)
    }

    private fun setActionBarTitle(title: String) {
        val text: Spannable = SpannableString(title)
        text.setSpan(
            ForegroundColorSpan(Color.WHITE),
            0,
            text.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        supportActionBar?.title = text

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}