package com.example.actearn.feature.home.student

import androidx.lifecycle.ViewModel
import com.example.actearn.core.PreferenceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudentHomeViewModel @Inject constructor(
    val preferenceHelper: PreferenceHelper
) : ViewModel(){
    fun logout() = preferenceHelper.sharedPreferences.edit().clear().apply()
}