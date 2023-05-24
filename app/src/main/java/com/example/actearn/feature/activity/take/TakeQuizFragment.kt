package com.example.actearn.feature.activity.take

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentTakeQuizBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TakeQuizFragment : BaseFragment<FragmentTakeQuizBinding>() {
    private val viewModel: TakeQuizViewModel by activityViewModels()
    override fun resId(): Int {
        return R.layout.fragment_take_quiz
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}