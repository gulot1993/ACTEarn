package com.example.actearn.feature.records

import androidx.lifecycle.ViewModel
import com.example.actearn.core.PreferenceHelper
import com.example.actearn.feature.activity.add.AddActivityState
import com.example.actearn.model.domain.User
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
class StudentRecordViewModel @Inject constructor(
    val repository: SharedRepository,
    val preferenceHelper: PreferenceHelper
) : ViewModel() {

    private val activities = mutableListOf<Activity>()

    val subjects = mutableListOf<Subject>()

    private val disposables = CompositeDisposable()

    private val _state by lazy {
        PublishSubject.create<RecordState>()
    }

    val state: Observable<RecordState> = _state

    fun getAllSubjects() {
        repository
            .getAllSubject()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                subjects.clear()
                subjects.addAll(it)
                _state.onNext(RecordState.Subjects(subjects))
            }
            .addTo(disposables)
    }

    fun getActivitiesBySubject(subjectId: Int): Single<List<Activity>> {
        return repository.getActivitiesByProfAndSubject(preferenceHelper.getLoggedInUser()!!.id, subjectId)
    }

    fun addActivities(list: List<Activity>) {
        this.activities.clear()
        this.activities.addAll(list)
    }

    fun getSelectedActivity(position: Int): Activity = this.activities[position]

    fun getQuestionByActivityId(activityId: Int): Single<List<Question>> = repository.getQuestionsByActivityId(activityId)

    fun getAnswersByQuestionId(questionId: Int, userId: Int): Single<StudentAnswer> = repository.getAllAnswersByQuestion(questionId, userId)

    fun getAnswersByQuestionId(questionId: Int): Single<List<StudentAnswer>> = repository.getAllAnswersByQuestionId(questionId)

    fun getDistinctUserId(): Single<List<Int>> = repository.getDistinctUserIdFromAnswers()

    fun getUserById(userId: Int): Single<List<UserWithPoint>> = repository.getUserAndPoints(userId)

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}