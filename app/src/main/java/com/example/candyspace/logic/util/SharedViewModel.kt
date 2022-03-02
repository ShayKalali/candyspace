package com.example.candyspace.logic.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.candyspace.data.model.User

class SharedViewModel : ViewModel() {
    private val selected: MutableLiveData<User> = MutableLiveData<User>()

    fun select(item: User) {
        selected.value = item
    }

    fun getSelected(): LiveData<User> = selected
}