package com.example.actearn.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ActivityWithRemarks(
    @Embedded val activity: Activity,
    @Relation(
        parentColumn = "activityId",
        entityColumn = "activityOwnerId"
    )
    val remarks: StudentRemarks?
)
