package com.example.actearn.feature.home.dsa

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentDsaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DSAHomeFragment : BaseFragment<FragmentDsaBinding>() {
    override fun resId(): Int {
        return R.layout.fragment_dsa
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding?.tvLogout?.setOnClickListener {
            findNavController()
                .navigate(
                    DSAHomeFragmentDirections.logoutNavigation()
                )
        }

        binding?.tvAddReward?.setOnClickListener {
            findNavController()
                .navigate(
                    R.id.action_dsaHomeFragment_to_addRewardFragment
                )
        }
    }
}