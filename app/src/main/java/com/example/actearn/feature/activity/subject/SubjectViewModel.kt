package com.example.actearn.feature.activity.subject

import androidx.lifecycle.ViewModel
import com.example.actearn.core.PreferenceHelper
import com.example.actearn.model.entity.Activity
import com.example.actearn.model.entity.Question
import com.example.actearn.model.entity.StudentAnswer
import com.example.actearn.model.entity.User
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    val repository: SharedRepository,
    val preferenceHelper: PreferenceHelper
): ViewModel() {
    private val professorsCopy = mutableListOf<User>()

    fun professors(users: List<User>) {
        professorsCopy.addAll(users)
    }

    fun getProfessorByIndex(index: Int): User = professorsCopy[index]

    fun getAllProfessors(): Single<List<User>> = repository.getAllProfessors()

    fun getActivityByProfAndSubject(profId: Int, subject: String): Single<List<Activity>> = repository.getActivitiesByProfAndSubject(profId, subject)

    fun getQuestionByActivityId(activityId: Int): Single<List<Question>> = repository.getQuestionsByActivityId(activityId)

    fun getAnswersByQuestionId(questionId: Int): Single<List<StudentAnswer>> = repository.getAllAnswersByQuestion(questionId, preferenceHelper.getLoggedInUser()!!.id)

    fun getCurrentLoggedInStudent(): com.example.actearn.model.domain.User = preferenceHelper.getLoggedInUser()!!
}