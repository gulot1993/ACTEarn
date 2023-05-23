package com.example.actearn.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Points(
    @PrimaryKey(autoGenerate = true) val pointsId: Int = 0,
    @ColumnInfo("userOwnerId") val userOwnerId: Int,
    @ColumnInfo("points") val points: Int
) {
    companion object {
        fun Points.toDomain(): com.example.actearn.model.domain.Points {
            return with(this) {
                com.example.actearn.model.domain.Points(
                    pointsId, userOwnerId, points
                )
            }
        }
    }
}