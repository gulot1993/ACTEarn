package com.example.actearn.model.modelview

import com.example.actearn.model.entity.Choices
import com.example.actearn.model.entity.Question

data class QuizQuestionChoicesModelView(
    val question: Question,
    var choices: MutableList<QuizChoicesModelView> = mutableListOf(),
)

data class QuizChoicesModelView(
    var choice: Choices,
    var isSelected: Boolean = false
)