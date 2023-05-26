package com.example.actearn.feature.home.dsa

import androidx.lifecycle.ViewModel
import com.example.actearn.model.entity.Reward
import com.example.actearn.model.entity.StudentRewardClaimed
import com.example.actearn.model.modelview.StudentClaimedReward
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class DSAViewModel @Inject constructor(
    val repository: SharedRepository
) : ViewModel() {
    fun getAllRewards(): Single<List<Reward>> = repository.getRewards()

    fun getAllStudentsRewards(studentId: Int): Single<List<StudentRewardClaimed>> = repository.getAllStudentsRewards(studentId)

    fun getReward(id: Int): Single<Reward> = repository.getReward(id)

    fun getAllClaimedRewards(): Single<List<StudentRewardClaimed>> = repository.getClaimedRewards()
}