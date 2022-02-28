package com.example.candyspace.logic.base

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel(private val context: Application) : ViewModel() {
    private val keyboardVisibility: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    open fun onBinding() {
    }

    fun setKeyboardVisibility(visible: Boolean) {
        keyboardVisibility.value = visible
    }

    fun showToastMessage(message: String?) {
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    fun getKeyboardVisibility(): LiveData<Boolean> = keyboardVisibility
}