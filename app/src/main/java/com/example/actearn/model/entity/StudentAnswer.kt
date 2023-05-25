package com.example.actearn.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StudentAnswer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("userId") val userId: Int,
    @ColumnInfo("questionOwnerId") val questionOwnerId: Int,
    @ColumnInfo("answerIndex") val answerIndex: Int,
    @ColumnInfo("isAnswerCorrect") val isAnswerCorrect: Boolean
)
