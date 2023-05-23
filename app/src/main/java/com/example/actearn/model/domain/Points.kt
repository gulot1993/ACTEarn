package com.example.actearn.model.domain

data class Points(
    val pointsId: Int,
    val userOwnerId: Int,
    val points: Int
) {
    companion object {
        fun Points.toEntity(): com.example.actearn.model.entity.Points {
            return with(this) {
                com.example.actearn.model.entity.Points(
                    pointsId, userOwnerId, points
                )
            }
        }
    }
}
