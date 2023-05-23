package com.example.actearn.feature.home.student

import androidx.lifecycle.ViewModel
import com.example.actearn.core.PreferenceHelper
import com.example.actearn.model.domain.User
import com.example.actearn.model.entity.UserWithPoint
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class StudentHomeViewModel @Inject constructor(
    val preferenceHelper: PreferenceHelper,
    val repository: SharedRepository
) : ViewModel(){
    fun logout() = preferenceHelper.sharedPreferences.edit().clear().apply()

    fun getLoggedInUser(): User? = repository.getLoggedInUser()

    fun getPointsAndUser(): Single<List<UserWithPoint>> = repository.getUserAndPoints(userId = getLoggedInUser()!!.id)

}