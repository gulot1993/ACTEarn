package com.example.actearn.feature.home.student

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentHomeFragment : BaseFragment<FragmentDashboardBinding>() {
    private val viewModel: StudentHomeViewModel by activityViewModels()
    override fun resId(): Int {
        return R.layout.fragment_dashboard
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding!!.tvLogout.setOnClickListener {
            viewModel.logout()
            findNavController()
                .navigate(
                    R.id.logoutNavigation
                )
        }
    }
}