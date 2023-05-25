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
import com.example.actearn.model.entity.Reward
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class DSAHomeFragment : BaseFragment<FragmentDsaBinding>() {

    private val viewModel: DSAViewModel by activityViewModels()

    private var adapter: RewardAdapter? = null

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
        super.onDestroy()
    }
}