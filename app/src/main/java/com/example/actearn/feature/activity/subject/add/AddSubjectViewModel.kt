package com.example.actearn.feature.activity.subject.add

import androidx.lifecycle.ViewModel
import com.example.actearn.model.entity.Subject
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

@HiltViewModel
class AddSubjectViewModel @Inject constructor(
    val repository: SharedRepository
) : ViewModel() {
    fun addSubject(subjectName: String): Completable = repository.saveSubject(Subject(name = subjectName))
}