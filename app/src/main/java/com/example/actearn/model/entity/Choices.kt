package com.example.actearn.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Choices(
    @PrimaryKey(autoGenerate = true) val choicesId: Int,
    @ColumnInfo("questionOwnerId") val questionOwnerId: Int,
    @ColumnInfo("choiceDescription") val choiceDescription: String,
    @ColumnInfo("correctAnswerId") val correctAnswerId: Int
)
