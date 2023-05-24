package com.example.actearn.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(
    @PrimaryKey(autoGenerate = true) val questionId: Int = 0,
    @ColumnInfo("question") val question: String,
    @ColumnInfo("activityId") val activityOwnerId: Int,
    @ColumnInfo("choicesCorrectAnswerIndex") val choicesCorrectAnswerIndex: Int
)
