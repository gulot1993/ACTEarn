package com.example.actearn.feature.activity.take

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentTakeQuizBinding
import com.example.actearn.feature.activity.take.adapter.TakeQuizQuestionAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.lang.String
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class TakeQuizFragment : BaseFragment<FragmentTakeQuizBinding>() {
    private val viewModel: TakeQuizViewModel by activityViewModels()
    private val args: TakeQuizFragmentArgs by navArgs()

    private var adapter: TakeQuizQuestionAdapter? = null

    private val disposables = CompositeDisposable()

    private var countDownTimer: CountDownTimer? = null
    override fun resId(): Int {
        return R.layout.fragment_take_quiz
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupListeners()
        startTimer()
    }

    private fun setupListeners() {
        binding!!.tvSubmit.setOnClickListener {
            viewModel.submitAnswers()
        }
    }

    private fun setupViewModel() {
        viewModel.getQuestionAndChoicesByActivityId(args.activityId)

        viewModel.questions.observe(viewLifecycleOwner) {
            it?.let {
                setupRecyclerView()
                adapter?.setItems(it)
            }
        }

        viewModel
            .state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                getState(it)
            }
            .addTo(compositeDisposable = disposables)
    }

    private fun getState(state: TakeQuizState) {
        when(state) {
            is TakeQuizState.NavigateBack -> {
                findNavController()
                    .popBackStack()
            }
            is TakeQuizState.StudentEarnedPoints -> {
                Toast.makeText(requireContext(), "Congratulations you earned ${state.points}", Toast.LENGTH_LONG).show()
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
        countDownTimer?.cancel()
        countDownTimer = null
        adapter = null
        disposables.clear()
        super.onDestroyView()
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(3600000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished).toInt()
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % TimeUnit.HOURS.toMinutes(1).toInt()
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % TimeUnit.MINUTES.toSeconds(
                        1
                    ).toInt()

                val timeLeftFormatted =
                    String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
                binding!!.tvTimer.setText(timeLeftFormatted)
            }

            override fun onFinish() {
                binding!!.tvTimer.setText("Time is Up!!")
                binding!!.tvSubmit.performClick()
            }
        }.start()
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        countDownTimer = null
        super.onDestroy()
    }
}