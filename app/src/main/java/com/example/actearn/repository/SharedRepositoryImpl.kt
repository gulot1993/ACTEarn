package com.example.actearn.repository

import com.example.actearn.core.PreferenceHelper
import com.example.actearn.database.AppDatabase
import com.example.actearn.model.entity.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SharedRepositoryImpl @Inject constructor(
    val preferenceHelper: PreferenceHelper,
    val database: AppDatabase
): SharedRepository {
    override fun isLoggedIn(): Single<Boolean> {
        return Single.just(preferenceHelper.getIsLoggedIn())
    }

    override fun signUp(
        username: String,
        password: String,
        fName: String,
        lName: String,
        role: String
    ): Completable {
        val entity = User(
            username = username,
            password = password,
            firstname = fName,
            lastname = lName,
            role = role,
            hasClaimedPoints = false
        )

        return database.userDao().saveUser(entity)
    }

    override fun signIn(username: String, password: String): Single<User> {
        return database.userDao().getUser(username, password)
    }

    override fun saveUser(user: com.example.actearn.model.domain.User) {
        preferenceHelper.setLoggedInUser(user)
    }

    override fun getLoggedInUser(): com.example.actearn.model.domain.User? {
        return preferenceHelper.getLoggedInUser()
    }

    override fun setIsLoggedIn(isLoggedIn: Boolean) {
        preferenceHelper.setIsLoggedIn(isLoggedIn)
    }
}