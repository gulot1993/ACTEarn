package com.example.actearn.feature.home.dsa

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentDsaBinding
import com.example.actearn.feature.home.dsa.adapter.RewardAdapter
import com.example.actearn.feature.home.dsa.adapter.StudentClaimedRewardAdapter
import com.example.actearn.model.entity.Reward
import com.example.actearn.model.entity.StudentRewardClaimed
import com.example.actearn.model.modelview.StudentClaimedReward
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class DSAHomeFragment : BaseFragment<FragmentDsaBinding>() {

    private val viewModel: DSAViewModel by activityViewModels()

    private var adapter: RewardAdapter? = null

    private var adapter2: StudentClaimedRewardAdapter? = null

    private val disposables = CompositeDisposable()
    override fun resId(): Int {
        return R.layout.fragment_dsa
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        getRewards()
    }

    private fun getRewards() {
        viewModel
            .getAllRewards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                setupRecyclerView(it)
            }
            .addTo(disposables)


        val studentClaimedReward = mutableListOf<StudentClaimedReward>()
        viewModel
            .getAllClaimedRewards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Observable.fromIterable(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy { studentReward ->
                        viewModel
                            .getReward(studentReward.rewardId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeBy { reward ->
                                viewModel
                                    .getAllStudentsRewards(studentReward.userId)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeBy {
                                        val user = it[0].userId
                                        viewModel
                                            .getUser(user)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribeBy {
                                                Timber.d("data:: ${it.firstname} ${reward.name}")
                                                studentClaimedReward.add(StudentClaimedReward(rewardNane = reward.name, "${it.firstname} ${it.lastname}"))

                                                adapter2 = StudentClaimedRewardAdapter(
                                                    requireContext(),
                                                    studentClaimedReward
                                                )

                                                binding!!.rvClaimedRewards.apply {
                                                    adapter = adapter2
                                                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                                                }
                                            }
                                            .addTo(disposables)
                                    }
                                    .addTo(disposables)
                            }
                            .addTo(disposables)
                    }
                    .addTo(disposables)
            }
            .addTo(disposables)
    }

    private fun setupRecyclerView(items: List<Reward>) {
        binding!!.rvRewards.apply {
            this@DSAHomeFragment.adapter = RewardAdapter(requireContext(), items)
            adapter = this@DSAHomeFragment.adapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
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

    override fun onDestroy() {
        adapter = null
        disposables.clear()
        adapter2 = null
        super.onDestroy()
    }
}