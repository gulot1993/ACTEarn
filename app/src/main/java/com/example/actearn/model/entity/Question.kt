package com.example.actearn.model.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Question(
    @PrimaryKey(autoGenerate = true) val questionId: Int,
    @ColumnInfo("question") val question: String,
    @ColumnInfo("activityId") val activityOwnerId: Int
)
