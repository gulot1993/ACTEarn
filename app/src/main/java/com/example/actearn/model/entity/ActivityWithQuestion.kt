package com.example.actearn.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ActivityWithQuestion(
    @Embedded val activity: Activity,
    @Relation(
        parentColumn = "activityId",
        entityColumn = "activityOwnerId"
    )
    val question: Question
)
