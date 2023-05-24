package com.example.actearn.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithActivity(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userOwnerId"
    )
    val points: List<Activity>
)
