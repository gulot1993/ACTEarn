package com.example.actearn.feature.profile

import androidx.lifecycle.ViewModel
import com.example.actearn.core.PreferenceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val preferenceHelper: PreferenceHelper
) : ViewModel(){
}