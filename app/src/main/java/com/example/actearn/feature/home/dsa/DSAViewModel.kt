package com.example.actearn.feature.home.dsa

import androidx.lifecycle.ViewModel
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSAViewModel @Inject constructor(
    val repository: SharedRepository
) : ViewModel() {
}