package com.example.actearn.feature.records

import androidx.lifecycle.ViewModel
import com.example.actearn.core.PreferenceHelper
import com.example.actearn.model.domain.User
import com.example.actearn.model.entity.Activity
import com.example.actearn.model.entity.Question
import com.example.actearn.model.entity.StudentAnswer
import com.example.actearn.model.entity.UserWithPoint
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class StudentRecordViewModel @Inject constructor(
    val repository: SharedRepository,
    val preferenceHelper: PreferenceHelper
) : ViewModel() {

    private val activities = mutableListOf<Activity>()

    fun getActivitiesBySubject(subjectName: String): Single<List<Activity>> {
        return repository.getActivitiesByProfAndSubject(preferenceHelper.getLoggedInUser()!!.id, subjectName)
    }

    fun addActivities(list: List<Activity>) {
        this.activities.clear()
        this.activities.addAll(list)
    }

    fun getSelectedActivity(position: Int): Activity = this.activities[position]

    fun getQuestionByActivityId(activityId: Int): Single<List<Question>> = repository.getQuestionsByActivityId(activityId)

    fun getAnswersByQuestionId(questionId: Int, userId: Int): Single<List<StudentAnswer>> = repository.getAllAnswersByQuestion(questionId, userId)

    fun getAnswersByQuestionId(questionId: Int): Single<List<StudentAnswer>> = repository.getAllAnswersByQuestionId(questionId)

    fun getDistinctUserId(): Single<List<Int>> = repository.getDistinctUserIdFromAnswers()

    fun getUserById(userId: Int): Single<List<UserWithPoint>> = repository.getUserAndPoints(userId)
}