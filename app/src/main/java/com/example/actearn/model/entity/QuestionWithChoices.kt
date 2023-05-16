package com.example.actearn.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class QuestionWithChoices(
    @Embedded val question: Question,
    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionOwnerId"
    )
    val choices: List<Choices>
)
