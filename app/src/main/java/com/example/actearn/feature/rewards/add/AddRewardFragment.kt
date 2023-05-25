package com.example.actearn.feature.rewards.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentAddRewardBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class AddRewardFragment : BaseFragment<FragmentAddRewardBinding>() {

    private val viewModel: AddRewardViewModel by activityViewModels()
    private val disposables = CompositeDisposable()
    override fun resId(): Int {
        return R.layout.fragment_add_reward
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding?.tvAddRewards?.setOnClickListener {
            viewModel
                .addReward(binding!!.etRewardName.text.toString(), binding!!.etRewardPoints.text.toString().toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    binding!!.etRewardName.text.clear()
                    binding!!.etRewardPoints.text.clear()
                    Toast.makeText(requireContext(), "Reward Added!", Toast.LENGTH_LONG).show()
                }
                .addTo(disposables)
        }
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}