package com.example.actearn.feature.activity.subject

import androidx.lifecycle.ViewModel
import com.example.actearn.core.PreferenceHelper
import com.example.actearn.feature.activity.take.TakeQuizState
import com.example.actearn.model.entity.*
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    val repository: SharedRepository,
    val preferenceHelper: PreferenceHelper
): ViewModel() {
    private val professorsCopy = mutableListOf<User>()

    val subjects = mutableListOf<Subject>()

    val disposables = CompositeDisposable()

    private val _state by lazy {
        PublishSubject.create<SubjectState>()
    }

    val state: Observable<SubjectState> = _state

    fun getAllSubjects() {
        repository
            .getAllSubject()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                subjects.clear()
                subjects.addAll(it)
                _state.onNext(SubjectState.Subjects(subjects))
            }
            .addTo(disposables)
    }

    fun professors(users: List<User>) {
        professorsCopy.addAll(users)
    }

    fun getProfessorByIndex(index: Int): User = professorsCopy[index]

    fun getAllProfessors(): Single<List<User>> = repository.getAllProfessors()

    fun getActivityByProfAndSubject(profId: Int, subjectId: Int): Single<List<Activity>> = repository.getActivitiesByProfAndSubject(profId, subjectId)

    fun getQuestionByActivityId(activityId: Int): Single<List<Question>> = repository.getQuestionsByActivityId(activityId)

    fun getAnswersByQuestionId(questionId: Int): Single<StudentAnswer> = repository.getAllAnswersByQuestion(questionId, preferenceHelper.getLoggedInUser()!!.id)

    fun getCurrentLoggedInStudent(): com.example.actearn.model.domain.User = preferenceHelper.getLoggedInUser()!!

    fun getStudentsAnswers(): Single<List<StudentAnswer>> = repository.getAllStudentAnswers()

    fun getStudentRemarks(profId: Int, subjectId: Int) = repository.getActivityRemarksByProfAndSubject(profId, subjectId)

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}