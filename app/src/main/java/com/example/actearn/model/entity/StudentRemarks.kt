package com.example.actearn.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StudentRemarks(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("activityOwnerId") val activityOwnerId: Int,
    @ColumnInfo("remarks") val remarks: String,
    @ColumnInfo("studentId") val studentId: Int
)
