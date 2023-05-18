package com.example.actearn.feature.splash

import androidx.lifecycle.ViewModel
import com.example.actearn.model.domain.User
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedRepository: SharedRepository
) : ViewModel() {
    fun isLoggedIn(): Single<Boolean> = sharedRepository.isLoggedIn()

    fun getUserLoggedIn(): User? = sharedRepository.getLoggedInUser()
}