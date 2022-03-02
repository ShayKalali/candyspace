package com.example.candyspace.ui.fragment.userdetail

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.candyspace.R
import com.example.candyspace.databinding.RowTagBinding
import javax.inject.Inject

class TopTagsRecyclerViewAdapter @Inject constructor() :
    RecyclerView.Adapter<TopTagsRecyclerViewAdapter.ViewHolder>() {
    private lateinit var listItems: MutableList<String>
    private lateinit var context: Context
    private val colours = mutableListOf(
        R.color.top_tag_1,
        R.color.top_tag_2,
        R.color.top_tag_3,
        R.color.top_tag_4,
        R.color.top_tag_5,
        R.color.top_tag_6
    )
    fun setData(items: List<String>) {
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
        context=parent.context
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tag = listItems[position]
        holder.bind(tag,colours,position,context)
    }

    class ViewHolder private constructor(private val binding: RowTagBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tag: String, colours: MutableList<Int>, position: Int, context: Context) {
            val selectedColour = colours[position % colours.size]
            binding.tvTag.text = tag
            ContextCompat.getDrawable(context, R.drawable.background_round)
            ViewCompat.setBackgroundTintList(
                binding.tvTag,
                ColorStateList.valueOf(ContextCompat.getColor(context, selectedColour))
            )

//            Glide.with(binding.ivtagProfileImage).load(tag.profileImage).apply(
//                RequestOptions().override(200, 200)).fitCenter().circleCrop().into(binding.ivtagProfileImage)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowTagBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}