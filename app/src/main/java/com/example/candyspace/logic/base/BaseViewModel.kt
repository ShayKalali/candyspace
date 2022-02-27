package com.example.candyspace.logic.base

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel(private val context: Application) : ViewModel() {

    open fun onBinding() {
    }

}