package com.example.actearn.feature.rewards.add

import androidx.lifecycle.ViewModel
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

@HiltViewModel
class AddRewardViewModel @Inject constructor(
    val repository: SharedRepository
) : ViewModel() {
    fun addReward(name: String, points: Int): Completable = repository.addReward(name, points)
}