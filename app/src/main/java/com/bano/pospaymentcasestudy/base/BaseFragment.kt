package com.bano.pospaymentcasestudy.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.viewbinding.ViewBinding
import com.bano.pospaymentcasestudy.databinding.FragmentPosBinding
import com.bano.pospaymentcasestudy.main.ViewModelFactory
import com.bano.pospaymentcasestudy.pos.POSViewModel

abstract class BaseFragment<VB: ViewBinding, VM: BaseViewModel> : Fragment() {

    lateinit var viewModel: VM
    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(getViewModelClass())
    }

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    abstract fun getViewModelClass(): Class<VM>
}