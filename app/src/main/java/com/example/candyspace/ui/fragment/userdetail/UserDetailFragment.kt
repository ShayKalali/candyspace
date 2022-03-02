package com.example.candyspace.ui.fragment.userdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.candyspace.R
import com.example.candyspace.databinding.FragmentUserDetailBinding
import com.example.candyspace.logic.base.BaseActivity
import com.example.candyspace.logic.base.BaseFragment
import com.example.candyspace.ui.activity.MainActivity
import com.google.android.flexbox.*
import javax.inject.Inject


class UserDetailFragment : BaseFragment<UserDetailViewModel, FragmentUserDetailBinding>() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: TopTagsRecyclerViewAdapter

    override var layout: Int = R.layout.fragment_user_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSharedViewModel().getSelected().observe(viewLifecycleOwner, { user ->
            if (user != null) {
                fragmentViewModel.setUserDetail(user)
                (requireActivity() as BaseActivity).setUpActionBar(true, user.name ?: "")
            }
        })

        fragmentViewModel.topTags.observe(viewLifecycleOwner, { topTags ->
            if (topTags != null) {
                adapter.setData(topTags)
                val layoutManager = FlexboxLayoutManager(requireContext())
                layoutManager.flexWrap = FlexWrap.WRAP
                layoutManager.flexDirection = FlexDirection.ROW
                layoutManager.justifyContent = JustifyContent.FLEX_START
                layoutManager.alignItems = AlignItems.FLEX_START
                binding.rvUserDetailTopTags.layoutManager = layoutManager
                binding.rvUserDetailTopTags.adapter = adapter
            }
        })
    }

    override fun getViewModel(): UserDetailViewModel {
        val viewModel: UserDetailViewModel by viewModels({ activity as MainActivity }) { viewModelProviderFactory }
        return viewModel
    }

    override fun onBackPressed(defaultBehaviour: () -> Unit): Boolean {
        (requireActivity() as BaseActivity).setUpActionBar(
            false,
            requireContext().getString(R.string.app_name)
        )
        fragmentViewModel.clearData()
        return super.onBackPressed(defaultBehaviour)
    }
}