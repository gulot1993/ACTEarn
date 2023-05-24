package com.example.actearn.feature.activity.subject

import androidx.lifecycle.ViewModel
import com.example.actearn.model.entity.Activity
import com.example.actearn.model.entity.User
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    val repository: SharedRepository
): ViewModel() {
    private val professorsCopy = mutableListOf<User>()
    fun getProfessorBySubject(subject: String) {

    }

    fun professors(users: List<User>) {
        professorsCopy.addAll(users)
    }

    fun getProfessorByIndex(index: Int): User = professorsCopy[index]

    fun getAllProfessors(): Single<List<User>> = repository.getAllProfessors()

    fun getActivityByProfAndSubject(profId: Int, subject: String): Single<List<Activity>> = repository.getActivitiesByProfAndSubject(profId, subject)
}