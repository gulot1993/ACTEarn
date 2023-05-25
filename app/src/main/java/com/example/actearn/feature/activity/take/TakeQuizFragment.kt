package com.example.actearn.feature.activity.take

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentTakeQuizBinding
import com.example.actearn.feature.activity.take.adapter.TakeQuizQuestionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TakeQuizFragment : BaseFragment<FragmentTakeQuizBinding>() {
    private val viewModel: TakeQuizViewModel by activityViewModels()
    private val args: TakeQuizFragmentArgs by navArgs()

    private var adapter: TakeQuizQuestionAdapter? = null
    override fun resId(): Int {
        return R.layout.fragment_take_quiz
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.getQuestionAndChoicesByActivityId(args.activityId)

        viewModel.questions.observe(viewLifecycleOwner) {
            it?.let {
                setupRecyclerView()
                adapter?.setItems(it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding?.rvQuizzes?.apply {
            this@TakeQuizFragment.adapter = TakeQuizQuestionAdapter(
                requireContext(),
                questionItemChanged = { data, position ->
                    viewModel.updateQuestion(data, position)
                }
            )
            adapter = this@TakeQuizFragment.adapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}