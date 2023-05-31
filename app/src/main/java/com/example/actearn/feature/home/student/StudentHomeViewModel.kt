package com.example.actearn.feature.home.student

import androidx.lifecycle.ViewModel
import com.example.actearn.core.PreferenceHelper
import com.example.actearn.model.domain.User
import com.example.actearn.model.entity.*
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
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

    fun getAllRewards(): Single<List<Reward>> = repository.getRewards()

    fun claimReward(rewardId: Int): Completable = repository.saveClaimedReward(rewardId)

    fun getAllClaimedRewards(): Single<List<StudentRewardClaimed>> = repository.getAllRewardsFromUser()

    fun getAllStudentsRewards(studentId: Int): Single<List<StudentRewardClaimed>> = repository.getAllStudentsRewards(studentId)

    fun getReward(id: Int): Single<Reward> = repository.getReward(id)

    fun updatePoints(points: Int): Completable {
        return repository.updatePoints(points)
    }

    fun getAllActivities(): Single<List<Activity>> = repository.getAllActivities()

    fun getUserPoints(userId: Int): Single<Points> {
        return repository.getUserPoints(userId)
    }
}