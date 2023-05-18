package com.example.actearn.feature.login

import androidx.lifecycle.ViewModel
import com.example.actearn.model.entity.User
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: SharedRepository
): ViewModel() {
    fun signIn(
        username: String,
        password: String
    ): Single<User> = repository.signIn(username, password)

    fun saveUser(user: com.example.actearn.model.domain.User) = repository.saveUser(user)

    fun setIsLoggedIn() = repository.setIsLoggedIn(true)
}