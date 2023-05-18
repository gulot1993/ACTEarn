package com.example.actearn.feature.signup

import androidx.lifecycle.ViewModel
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val sharedRepository: SharedRepository
): ViewModel() {
    fun signUp(
        username: String,
        password: String,
        fName: String,
        lName: String,
        role: String
    ): Completable {
        return sharedRepository.signUp(username, password, fName, lName, role)
    }
}