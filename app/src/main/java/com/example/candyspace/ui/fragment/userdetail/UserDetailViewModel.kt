package com.example.candyspace.ui.fragment.userdetail

import android.app.Application
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.candyspace.data.model.User
import com.example.candyspace.data.repository.UserRepository
import com.example.candyspace.logic.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class UserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    val context: Application
) :
    BaseViewModel(context) {

    var userAvatarUrl = MutableLiveData<String>().apply {
        value = ""
    }

    var userName = MutableLiveData<String>().apply {
        value = ""
    }
    var reputation = MutableLiveData<String>().apply {
        value = ""
    }

    var topTags = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf()
    }
    var goldBadge = MutableLiveData<String>().apply {
        value = ""
    }
    var silverBadge = MutableLiveData<String>().apply {
        value = ""
    }
    var bronzeBadge = MutableLiveData<String>().apply {
        value = ""
    }
    var location = MutableLiveData<String>().apply {
        value = null
    }
    var creationDate = MutableLiveData<String>().apply {
        value = ""
    }

    fun setUserDetail(user: User) {
        userAvatarUrl.value = user.profileImage
        userName.value = user.name
        reputation.value = user.reputation.toString()
        goldBadge.value = user.goldBadgeCounts.toString()
        silverBadge.value = user.silverBadgeCounts.toString()
        bronzeBadge.value = user.bronzeBadgeCounts.toString()
        location.value = user.location
        creationDate.value = user.creationDate?.toString()

        viewModelScope.launch {
            val userTopTagsResult = userRepository.getUserTopTags(user.id)

            if (userTopTagsResult.isSuccess) {
                topTags.value = userTopTagsResult.getOrNull()?.toMutableList() ?: mutableListOf()
            } else {
                showToastMessage(userTopTagsResult.exceptionOrNull()?.message)
            }
        }
    }

    fun clearData() {
        userAvatarUrl.value = null
        userName.value = null
        reputation.value = null
        topTags.value = mutableListOf()
        goldBadge.value = null
        silverBadge.value = null
        bronzeBadge.value = null
        location.value = null
        creationDate.value = null
    }

    companion object {

        @JvmStatic
        @BindingAdapter("glide")
        fun glide(view: AppCompatImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(view).load(url).override(500, 500).circleCrop().into(view)
            }
        }
    }
}
