package com.example.actearn.feature.home.student

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentDashboardBinding
import com.example.actearn.feature.home.student.adapter.StudentRewardAdapter
import com.example.actearn.model.entity.Reward
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class StudentHomeFragment : BaseFragment<FragmentDashboardBinding>() {
    private val viewModel: StudentHomeViewModel by activityViewModels()

    private val disposables = CompositeDisposable()
    override fun resId(): Int {
        return R.layout.fragment_dashboard
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupViews()
    }

    private fun setupViews() {
        getPoints()
    }

    private fun getPoints() {
        var points = 0
        viewModel
            .getPointsAndUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { user ->
                binding?.tvPoints?.text = user[0].points.sumOf { it.points }.toString()
            }
            .addTo(disposables)
    }

    private fun setupListeners() {
        binding!!.tvLogout.setOnClickListener {
            viewModel.logout()
            findNavController()
                .navigate(
                    R.id.logoutNavigation
                )
        }

        binding!!.imageView3.setOnClickListener {
            findNavController()
                .navigate(
                    R.id.action_studentHomeFragment_to_subjectFragment
                )
        }

        binding!!.imageView7.setOnClickListener {
            findNavController()
                .navigate(
                    R.id.action_studentHomeFragment_to_profileFragment
                )
        }

        binding!!.imageView6.setOnClickListener {
            viewModel
                .getAllRewards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { items ->
                    val filteredItems = items.filter { it.points <= binding!!.tvPoints.text.toString().toInt() }
                    if (filteredItems.isNotEmpty()) {
                        showRewardsBottomSheet(filteredItems)
                    } else {
                        Toast.makeText(requireContext(), "Points not enough!", Toast.LENGTH_LONG).show()
                    }
                }
                .addTo(disposables)
        }
    }

    private fun showRewardsBottomSheet(items: List<Reward>) {
        val bottomsheet = BottomSheetDialog(requireContext())
        bottomsheet.setContentView(R.layout.layout_reward_claim_bottomsheet)
        val rewards = bottomsheet.findViewById<RecyclerView>(R.id.rvRewards)

        val rewardAdapter = StudentRewardAdapter(requireContext(), items) {
            viewModel
                .claimReward(it.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    val points = binding!!.tvPoints.text.toString().toInt()
                    val minusPoints = points - it.points
                    viewModel
                        .updatePoints(minusPoints)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy {
                            binding?.tvPoints?.text = minusPoints.toString()
                            Toast.makeText(requireContext(), "Reward claimed!", Toast.LENGTH_LONG).show()
                            bottomsheet.dismiss()
                        }
                        .addTo(disposables)
                }
                .addTo(disposables)
        }
        rewards!!.apply {
            adapter = rewardAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        bottomsheet.show()
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }
}