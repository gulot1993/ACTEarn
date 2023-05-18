package com.example.actearn.feature.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun resId(): Int {
        return R.layout.fragment_login
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewListeners()
    }

    private fun setupViewListeners() {
        binding?.tvRegister?.setOnClickListener {
            findNavController()
                .navigate(
                    LoginFragmentDirections
                        .actionLoginFragmentToSignUpFragment()
                )
        }
    }
}