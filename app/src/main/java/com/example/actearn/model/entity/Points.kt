package com.example.actearn.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Points(
    @PrimaryKey(autoGenerate = true) val pointsId: Int,
    val userOwnerId: Int,
    val points: Int
)