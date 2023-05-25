package com.example.actearn.feature.activity.take

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.actearn.model.modelview.ChoicesModelView
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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TakeQuizViewModel @Inject constructor(
    val repository: SharedRepository
) : ViewModel(){

    private val disposables = CompositeDisposable()

    private val _questions = MutableLiveData<MutableList<QuizQuestionChoicesModelView>>()
    val questions: LiveData<MutableList<QuizQuestionChoicesModelView>>
        get() = _questions

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
                                    val choices = it.choices
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

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}