package com.example.actearn.model.modelview


data class QuestionChoicesModelView(
    var question: String = "",
    var choices: MutableList<ChoicesModelView> = mutableListOf(),
    var correctAnswerIndex: Int = -1
)

data class ChoicesModelView(
    var choice: String,
    var isSelected: Boolean = false
)
