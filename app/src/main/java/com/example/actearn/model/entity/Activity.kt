package com.example.actearn.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Activity(
   @PrimaryKey(true) val activityId: Int = 0,
   @ColumnInfo("activityName") val activityName: String,
   @ColumnInfo("subject") val subject: String,
   @ColumnInfo("userOwnerId") val userOwnerId: Int
)
