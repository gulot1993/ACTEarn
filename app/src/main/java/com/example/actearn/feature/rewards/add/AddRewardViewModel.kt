package com.example.actearn.feature.rewards.add

import androidx.lifecycle.ViewModel
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddRewardViewModel @Inject constructor(
    val repository: SharedRepository
) : ViewModel() {
}