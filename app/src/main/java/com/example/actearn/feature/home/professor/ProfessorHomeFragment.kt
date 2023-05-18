package com.example.actearn.feature.home.professor

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentProfessorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfessorHomeFragment : BaseFragment<FragmentProfessorBinding>(){

    private val viewModel: ProfessorViewModel by activityViewModels()

    override fun resId(): Int {
        return R.layout.fragment_professor
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewListeners()
    }

    private fun setupViewListeners() {
        binding?.tvLogout?.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.logoutNavigation)
        }
    }
}