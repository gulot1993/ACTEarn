package com.example.actearn.feature.activity.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.actearn.core.PreferenceHelper
import com.example.actearn.feature.activity.take.TakeQuizState
import com.example.actearn.model.entity.Activity
import com.example.actearn.model.entity.Choices
import com.example.actearn.model.entity.Subject
import com.example.actearn.model.modelview.ChoicesModelView
import com.example.actearn.model.modelview.QuestionChoicesModelView
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddActivityViewModel @Inject constructor(
    val repository: SharedRepository
) : ViewModel() {
    private val _questionsWithChoices = MutableLiveData<MutableList<QuestionChoicesModelView>>()
    val questionsWithChoices: LiveData<MutableList<QuestionChoicesModelView>>
        get() = _questionsWithChoices

    private val _doneSaving = MutableLiveData<Boolean>(false)
    val doneSaving: LiveData<Boolean>
        get() = _doneSaving

    private val disposables = CompositeDisposable()

    val subjects = mutableListOf<Subject>()

    private val _state by lazy {
        PublishSubject.create<AddActivityState>()
    }

    val state: Observable<AddActivityState> = _state

    fun getAllSubjects() {
        repository
            .getAllSubject()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                subjects.clear()
                subjects.addAll(it)
                _state.onNext(AddActivityState.Subjects(subjects))
            }
            .addTo(disposables)
    }
    fun addEmptyQuestionaire() {
        var questionaires = _questionsWithChoices.value
        if (questionaires == null) questionaires = mutableListOf()
        val choices = mutableListOf<ChoicesModelView>()
        for (i in 0 until 4) {
            choices.add(ChoicesModelView(choice = ""))
        }
        questionaires.add(
            QuestionChoicesModelView(
                choices = choices
            )
        )
        _questionsWithChoices.value = questionaires!!
    }

    fun updateQuestionaire(data: QuestionChoicesModelView, position: Int) {
        val questions = _questionsWithChoices.value
        val question = questions!![position]
        question.question = data.question
        question.choices = data.choices
        questions[position] = question
    }

    fun saveQuestions(
        activity: String,
        subjectId: Int
    ): Completable {
        return repository.saveActivity(activity, subjectId)
    }

    fun getActivityByName(name: String): Single<Activity> = repository.getActivityByName(name)

    fun insertQuestions(activityId: Int) {
        val questionsAndChoices = _questionsWithChoices.value
        questionsAndChoices?.let {
            val questions = questionsAndChoices.map { it.question }
            val choicesTotal = questionsAndChoices.map { it.choices }.count()
            val savedChoice = mutableListOf<String>()
            Observable.fromIterable(questions)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { question ->
                    val choices = questionsAndChoices.find { it.question == question}?.choices
                    val correctAnswer = choices?.find { it.isSelected }
                    val index = choices!!.indexOf(correctAnswer)
                    repository.saveQuestion(question, activityId, index)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy {
                            repository.getQuestionByDescription(question)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeBy { questionEntity ->
                                    Observable.fromIterable(choices!!.map { it.choice })
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeBy { choice ->
                                            val findChoice = choices.find { it.choice == choice }
                                            val index = choices.indexOf(findChoice)
                                            repository.saveChoices(
                                                Choices(
                                                    questionOwnerId = questionEntity.questionId,
                                                    choiceDescription = choice,
                                                    index = index
                                                )
                                            )
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeBy {
                                                    savedChoice.add(choice)
                                                    if (savedChoice.count() == choicesTotal) {
                                                        _doneSaving.value = true
                                                    }
                                                }
                                                .addTo(disposables)
                                        }
                                        .addTo(disposables)
                                }
                                .addTo(disposables)
                        }
                        .addTo(disposables)
                }
                .addTo(disposables)
        }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    fun resetDoneSaving() {
        _questionsWithChoices.value?.clear()
        _questionsWithChoices.value = mutableListOf()
        _doneSaving.value = false
    }
}