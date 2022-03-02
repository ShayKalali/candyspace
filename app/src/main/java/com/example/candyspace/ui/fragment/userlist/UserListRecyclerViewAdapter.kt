package com.example.candyspace.ui.fragment.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.candyspace.data.model.User
import com.example.candyspace.databinding.RowUserBinding
import javax.inject.Inject

class UserListRecyclerViewAdapter @Inject constructor() :
    RecyclerView.Adapter<UserListRecyclerViewAdapter.ViewHolder>() {
    private lateinit var listItems: MutableList<User>
    var onItemClicked: ((User) -> Unit)? = null

    fun setData(items: List<User>) {
        if (this::listItems.isInitialized)
            listItems.clear()
        listItems = items.toMutableList()
    }

    override fun getItemCount(): Int {
        return if (this::listItems.isInitialized)
            listItems.size
        else
            0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listItems[position]
        holder.bind(user, onItemClicked)
    }

    class ViewHolder private constructor(private val binding: RowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, onItemClicked: ((User) -> Unit)?) {
            binding.tvUserReputation.text = user.reputation.toString()
            binding.tvUserName.text = user.name.toString()
            binding.tvViewProfile.setOnClickListener {
                onItemClicked?.invoke(user)
            }
            Glide.with(binding.ivUserProfileImage).load(user.profileImage).apply(
                RequestOptions().override(200, 200)).fitCenter().circleCrop().into(binding.ivUserProfileImage)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowUserBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}