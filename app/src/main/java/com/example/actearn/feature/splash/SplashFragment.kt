package com.example.actearn.feature.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val disposables = CompositeDisposable()

    private val viewModel: SplashViewModel by activityViewModels()
    override fun resId(): Int {
        return R.layout.fragment_splash
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    private fun getData() {
        // check current logged in nga user
        viewModel.isLoggedIn()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (!it) {
                        findNavController()
                            .navigate(
                                SplashFragmentDirections
                                    .actionSplashFragmentToLoginFragment()
                            )
                    } else {
                        viewModel.getUserLoggedIn()?.let {
                            // prof , student, dsa
                            if (it.role == "Professor") {
                                findNavController()
                                    .navigate(
                                        SplashFragmentDirections
                                            .actionSplashFragmentToProfessorHomeFragment()
                                    )
                            } else if (it.role == "Student"){
                                findNavController()
                                    .navigate(
                                        SplashFragmentDirections
                                            .actionSplashFragmentToStudentHomeFragment()
                                    )
                            } else {
                                findNavController()
                                    .navigate(
                                        R.id.action_splashFragment_to_dsaHomeFragment
                                    )
                            }
                        }
                    }
                },
                onError = {

                }
            )
            .addTo(disposables)
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }
}