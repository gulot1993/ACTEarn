package com.example.actearn.model.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val username: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val role: String,
    var hasClaimedPoints: Boolean
) : Parcelable {
    companion object {
        fun toEntity(user: User): com.example.actearn.model.entity.User {
            return with(user) {
                com.example.actearn.model.entity.User(
                    userId = id,
                    username = username,
                    password = password,
                    firstname = firstname,
                    lastname = lastname,
                    role = role,
                    hasClaimedPoints = hasClaimedPoints
                )
            }
        }
    }
}
