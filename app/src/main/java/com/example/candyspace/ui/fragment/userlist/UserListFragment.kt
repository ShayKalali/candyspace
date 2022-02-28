package com.example.candyspace.ui.fragment.userlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.candyspace.R
import com.example.candyspace.databinding.FragmentUserListBinding
import com.example.candyspace.logic.base.BaseActivity
import com.example.candyspace.logic.base.BaseFragment
import com.example.candyspace.ui.activity.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject


class UserListFragment : BaseFragment<UserListViewModel, FragmentUserListBinding>() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: UserListRecyclerViewAdapter

    override var layout: Int = R.layout.fragment_user_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fragmentViewModel.userList.observe(viewLifecycleOwner, { userList ->
            if (userList != null) {

                if (adapter.itemCount == 0) {
                    adapter.setData(userList)
                    binding.rvUserList.adapter = adapter
                } else {
                    adapter.setData(userList)
                    binding.rvUserList.adapter?.notifyDataSetChanged()
                }
                adapter.onItemClicked = {
                    getSharedViewModel().select(it)
                }
            }
        })
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(
            requireActivity(),
            R.style.ThemeOverlay_MaterialComponents_Dialog_Alert
        )
            .setTitle(getString(R.string.dialog_exit_title))
            .setMessage(getString(R.string.dialog_exit_message))
            .setPositiveButton(getString(R.string.dialog_exit_yes)) { _, _ -> requireActivity().finish() }
            .setNegativeButton(getString(R.string.dialog_exit_no)) { _dialog, _ -> _dialog.dismiss() }
            .show()
    }

    override fun onBackPressed(defaultBehaviour: () -> Unit): Boolean {
        showConfirmationDialog()
        return false
    }


    override fun getViewModel(): UserListViewModel {
        val viewModel: UserListViewModel by viewModels({ activity as MainActivity }) { viewModelProviderFactory }
        return viewModel
    }
}