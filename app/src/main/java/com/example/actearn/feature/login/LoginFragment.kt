package com.example.actearn.feature.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.actearn.R
import com.example.actearn.core.BaseFragment
import com.example.actearn.databinding.FragmentLoginBinding
import com.example.actearn.model.domain.User
import com.example.actearn.model.entity.User.Companion.toDomain
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

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
        // click event
        binding?.tvRegister?.setOnClickListener {
            findNavController()
                .navigate(
                    LoginFragmentDirections
                        .actionLoginFragmentToSignUpFragment()
                )
        }

        binding?.btnLogin?.setOnClickListener {
            signIn(
                binding!!.etEmail.text.toString(), // professor
                binding!!.etPassword.text.toString() // password
            )
        }
    }

    private fun signIn(userName: String, password: String) {
        viewModel
            .signIn(
                userName,
                password
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {
                      Toast.makeText(requireContext(), "No users found", Toast.LENGTH_LONG).show()
                },
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
                    } else if (userDomain.role == "Student"){
                        Timber.d("logged in: ${userDomain.hasClaimedPoints}")
                        if (!userDomain.hasClaimedPoints) {
                            userDomain.hasClaimedPoints = true
                            claimPoints(userDomain)
                        } else {
                            navigateToStudentHome()
                        }

                    } else {
                        findNavController()
                            .navigate(
                                R.id.action_loginFragment_to_dsaHomeFragment
                            )
                    }
                }
            )
            .addTo(disposables)
    }

    private fun claimPoints(user: User) {
        viewModel
            .savePoints(user.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                updateUserClaimedPoints(user)
            }
            .addTo(disposables)
    }

    private fun navigateToStudentHome() {
        findNavController()
            .navigate(
                LoginFragmentDirections
                    .actionLoginFragmentToStudentHomeFragment()
            )
    }

    private fun updateUserClaimedPoints(user: User) {
        viewModel
            .updateUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                navigateToStudentHome()
            }
            .addTo(disposables)
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }
}