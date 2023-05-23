package com.example.actearn.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "firstname") val firstname: String,
    @ColumnInfo(name = "lastname") val lastname: String,
    @ColumnInfo(name = "role") val role: String,
    @ColumnInfo(name = "hasClaimedPoints") val hasClaimedPoints: Boolean? = null
) {
    companion object {
        fun User.toDomain(): com.example.actearn.model.domain.User {
            return com.example.actearn.model.domain.User(
                userId, username, password, firstname, lastname, role, hasClaimedPoints ?: false
            )
        }
    }
}
