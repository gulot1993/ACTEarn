package com.example.actearn.feature.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(){

    private val viewModel: ProfileViewModel by activityViewModels()
    override fun resId(): Int {
        return R.layout.fragment_profile
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        binding?.let {
            with(binding!!) {
                val user = viewModel.preferenceHelper.getLoggedInUser()
                tvUserFullName.text = "${user!!.firstname} ${user!!.lastname}"
                tvRole.text = user!!.role
            }
        }
    }
}