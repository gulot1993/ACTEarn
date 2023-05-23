package com.example.actearn.feature.home.student

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
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
        viewModel
            .getPointsAndUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                binding?.tvPoints?.text = it[0].points.sumOf { it.points }.toString()
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
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }
}