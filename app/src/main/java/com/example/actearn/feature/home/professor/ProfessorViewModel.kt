package com.example.actearn.feature.home.professor

import androidx.lifecycle.ViewModel
import com.example.actearn.core.PreferenceHelper
import com.example.actearn.model.domain.User
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfessorViewModel @Inject constructor(
    val preferenceHelper: PreferenceHelper,
    val repository: SharedRepository
): ViewModel() {
    fun logout() = preferenceHelper.sharedPreferences.edit().clear().apply()

    fun getLoggedInUser(): User = repository.getLoggedInUser()!!
}