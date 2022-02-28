package com.example.candyspace.ui.fragment.userlist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.candyspace.data.model.User
import com.example.candyspace.data.repository.UserRepository
import com.example.candyspace.logic.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository,
    val context: Application
) :
    BaseViewModel(context) {
    var isRvVisible = MutableLiveData<Boolean>().apply {
        value = false
    }
    var userList = MutableLiveData<List<User>>().apply {
        value = null
    }

    var etUserName = MutableLiveData<String>().apply {
        value = null
    }
    var isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }

    private var lastSearchText: String? = null

    override fun onBinding() {
        super.onBinding()

        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            isLoading.value = true
            val usersResult = userRepository.getUsers()

            if (usersResult.isSuccess) {
                isRvVisible.value = true
                userList.value = usersResult.getOrNull()
            } else {
                showToastMessage(usersResult.exceptionOrNull()?.message)
            }
            isLoading.value = false
        }
    }

    fun onSearchUser() {
        if (etUserName.value == lastSearchText) {
            return
        }
        isLoading.value = true
        viewModelScope.launch {
            val usersResult = userRepository.getUsers(etUserName.value)
            if (usersResult.isSuccess) {
                setKeyboardVisibility(false)
                isRvVisible.value = true
                userList.value = usersResult.getOrNull()
                lastSearchText = etUserName.value
            } else {
                showToastMessage(usersResult.exceptionOrNull()?.message)
            }
            isLoading.value = false
        }
    }
}