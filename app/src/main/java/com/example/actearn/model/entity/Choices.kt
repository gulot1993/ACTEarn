package com.example.actearn.model.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Choices(
    @PrimaryKey(autoGenerate = true) val choicesId: Int,
    val questionOwnerId: Int,
)
