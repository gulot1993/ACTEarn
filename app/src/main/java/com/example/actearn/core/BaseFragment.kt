package com.example.actearn.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B: ViewDataBinding> : Fragment(){
    var binding: B? = null

    @LayoutRes
    abstract fun resId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(
                inflater,
                resId(),
                container,
                false
            )
        binding!!.lifecycleOwner = viewLifecycleOwner
        return binding!!.root
    }

    override fun onDestroy() {
        binding!!.unbind()
        binding = null
        super.onDestroy()
    }
}