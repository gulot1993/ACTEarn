package com.example.actearn.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rewards(
    @PrimaryKey(autoGenerate = true) val rewardId: Int,
    @ColumnInfo("reward") val reward: String,
    @ColumnInfo("points") val points: Int
)
