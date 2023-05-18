package com.example.actearn.feature.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentLoginBinding
import com.example.actearn.model.entity.User.Companion.toDomain
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val disposables = CompositeDisposable()

    private val viewModel: LoginViewModel by activityViewModels()
    override fun resId(): Int {
        return R.layout.fragment_login
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewListeners()
    }

    private fun setupViewListeners() {
        binding?.tvRegister?.setOnClickListener {
            findNavController()
                .navigate(
                    LoginFragmentDirections
                        .actionLoginFragmentToSignUpFragment()
                )
        }

        binding?.btnLogin?.setOnClickListener {
            viewModel
                .signIn(
                    binding!!.etEmail.text.toString(),
                    binding!!.etPassword.text.toString()
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = { },
                    onSuccess = {
                        val userDomain = it.toDomain()
                        viewModel.saveUser(userDomain)
                        viewModel.setIsLoggedIn()

                        if (userDomain.role == "Professor") {
                            findNavController()
                                .navigate(
                                    LoginFragmentDirections
                                        .actionLoginFragmentToProfessorHomeFragment()
                                )
                        } else {
                            findNavController()
                                .navigate(
                                    LoginFragmentDirections
                                        .actionLoginFragmentToStudentHomeFragment()
                                )
                        }
                    }
                )
                .addTo(disposables)
        }
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }
}