package com.example.actearn.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reward(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("points") val points: Int
)
