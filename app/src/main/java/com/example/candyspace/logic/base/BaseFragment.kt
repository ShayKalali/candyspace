package com.example.candyspace.logic.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import com.example.candyspace.BR
import com.example.candyspace.logic.util.SharedViewModel
import dagger.android.support.DaggerFragment

abstract class BaseFragment<TViewModel : BaseViewModel, TViewDataBinding : ViewDataBinding> :
    DaggerFragment() {

    lateinit var fragmentTag: String
    protected lateinit var fragmentViewModel: TViewModel
    protected abstract var layout: Int
    private lateinit var mView: View
    lateinit var binding: TViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentViewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater,
            layout,
            container,
            false
        )
        binding.setVariable(BR.viewModel, fragmentViewModel)
        binding.lifecycleOwner = viewLifecycleOwner
        mView = binding.root
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentViewModel.onBinding()
        onBinding()
    }

    protected abstract fun getViewModel(): TViewModel

    fun requireBaseActivity(): BaseActivity {
        return requireActivity() as BaseActivity
    }

    open fun onBinding() {
        fragmentViewModel.getKeyboardVisibility().observe(this, {
            setKeyboardVisibility(it)
        })
    }

    fun getSharedViewModel(): SharedViewModel {
        val viewModel: SharedViewModel by activityViewModels()
        return viewModel
    }

    open fun onBackPressed(defaultBehaviour: () -> Unit): Boolean {
        defaultBehaviour()
        return true
    }

    private fun setKeyboardVisibility(show: Boolean) {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        val view = requireActivity().findViewById<View>(android.R.id.content)
        if (show) {
            imm?.showSoftInput(view, 0)
        } else {
            imm?.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }
}