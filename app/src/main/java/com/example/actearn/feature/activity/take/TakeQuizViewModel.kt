package com.example.actearn.feature.activity.take

import androidx.lifecycle.ViewModel
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TakeQuizViewModel @Inject constructor(
    val repository: SharedRepository
) : ViewModel(){
}