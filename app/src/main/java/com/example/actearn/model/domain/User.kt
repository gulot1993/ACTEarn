package com.example.actearn.model.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val role: String
) : Parcelable
