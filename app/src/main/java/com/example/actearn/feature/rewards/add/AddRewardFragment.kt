package com.example.actearn.feature.rewards.add

import android.os.Bundle
import android.view.View
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentAddRewardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRewardFragment : BaseFragment<FragmentAddRewardBinding>() {
    override fun resId(): Int {
        return R.layout.fragment_add_reward
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}