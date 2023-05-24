package com.example.actearn.feature.activity.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.actearn.core.PreferenceHelper
import com.example.actearn.model.modelview.ChoicesModelView
import com.example.actearn.model.modelview.QuestionChoicesModelView
import com.example.actearn.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddActivityViewModel @Inject constructor(
    val preferenceHelper: PreferenceHelper,
    val repository: SharedRepository
) : ViewModel() {
    private val _questionsWithChoices = MutableLiveData<MutableList<QuestionChoicesModelView>>()
    val questionsWithChoices: LiveData<MutableList<QuestionChoicesModelView>>
        get() = _questionsWithChoices


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
}