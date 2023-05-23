package com.example.actearn.repository

import com.example.actearn.model.entity.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface SharedRepository {
    fun isLoggedIn(): Single<Boolean>

    fun signUp(
        username: String,
        password: String,
        fName: String,
        lName: String,
        role: String
    ): Completable

    fun signIn(
        username: String,
        password: String
    ): Single<User>

    fun saveUser(user: com.example.actearn.model.domain.User)

    fun getLoggedInUser(): com.example.actearn.model.domain.User?

    fun setIsLoggedIn(isLoggedIn: Boolean)

    fun updateUser(user: com.example.actearn.model.domain.User): Completable

    fun savePoints(points: Int, userOwnerId: Int): Completable
}