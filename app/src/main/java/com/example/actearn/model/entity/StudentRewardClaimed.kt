package com.example.actearn.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StudentRewardClaimed(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("userId") val userId: Int,
    @ColumnInfo("rewardId") val rewardId: Int
)