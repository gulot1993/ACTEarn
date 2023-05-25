package com.example.actearn.feature.activity.take

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.actearn.core.PreferenceHelper
import com.example.actearn.model.entity.Question
import com.example.actearn.model.modelview.QuizChoicesModelView
import com.example.actearn.model.modelview.QuizQuestionChoicesModelView
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TakeQuizViewModel @Inject constructor(
    val repository: SharedRepository,
    val preferenceHelper: PreferenceHelper
) : ViewModel(){

    private val disposables = CompositeDisposable()

    private val _questions = MutableLiveData<MutableList<QuizQuestionChoicesModelView>>()
    val questions: LiveData<MutableList<QuizQuestionChoicesModelView>>
        get() = _questions

    private val _state by lazy {
        PublishSubject.create<TakeQuizState>()
    }

    val state: Observable<TakeQuizState> = _state

    private val _doneSaving = MutableLiveData<Boolean>(false)
    val doneSaving: LiveData<Boolean>
        get() = _doneSaving

    fun updateQuestion(data: QuizQuestionChoicesModelView, position: Int) {
        val existingQuestions = _questions.value
        existingQuestions?.let {
            val existingQuestion = existingQuestions[position]
            existingQuestion.choices = data.choices
        }
    }
    fun getQuestionAndChoicesByActivityId(activityId: Int) {
        val modelView = mutableListOf<QuizQuestionChoicesModelView>()
        repository
            .getQuestionsByActivityId(activityId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Observable.fromIterable(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy { question ->
                        repository
                            .getAllQuestions(question.questionId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeBy { questionWithChoices ->
                                questionWithChoices.map {
                                    val modelView2 = mutableListOf<QuizChoicesModelView>()
                                    val question = it.question
                                    it.choices.map {
                                        modelView2.add(QuizChoicesModelView(it))
                                    }
                                    modelView.add(QuizQuestionChoicesModelView(question, modelView2))
                                    _questions.value = modelView
                                }
                            }
                            .addTo(disposables)
                    }
                    .addTo(disposables)
            }
            .addTo(disposables)
    }

    fun submitAnswers() {
        val existingQuestions = _questions.value
        val savedQuestions = mutableListOf<Question>()
        val correctAnswersCount = mutableListOf<Boolean>()

        Observable.fromIterable(existingQuestions!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { questionAndChoices ->
                val question = questionAndChoices.question
                val studentChoiceSelected = questionAndChoices.choices.find { it.isSelected }
                val indexChoiceSelected = questionAndChoices.choices.indexOf(studentChoiceSelected)
                val indexCorrectAnswer = question.choicesCorrectAnswerIndex
                val questionOwnerId = question.questionId
                val isAnswerCorrect = indexChoiceSelected == indexCorrectAnswer

                if (isAnswerCorrect) {
                    correctAnswersCount.add(isAnswerCorrect)
                }

                repository
                    .saveStudentAnswer(
                        questionOwnerId,
                        indexChoiceSelected,
                        isAnswerCorrect
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy {
                        savedQuestions.add(question)
                        if (existingQuestions.size == savedQuestions.size) {

                            val average = (correctAnswersCount.size.toFloat() / existingQuestions.size.toFloat()) * 100F
                            if (average.toInt() >= 80) {
                                // add points
                                repository
                                    .savePoints(1, preferenceHelper.getLoggedInUser()!!.id)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeBy {
                                        _state.onNext(
                                            TakeQuizState.StudentEarnedPoints(1)
                                        )
                                        _state.onNext(
                                            TakeQuizState.NavigateBack
                                        )
                                    }
                                    .addTo(disposables)
                            } else {
                                _state.onNext(
                                    TakeQuizState.NavigateBack
                                )
                            }
                        }
                    }
                    .addTo(disposables)
            }
            .addTo(compositeDisposable = disposables)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}