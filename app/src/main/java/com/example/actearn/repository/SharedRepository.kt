package com.example.actearn.repository

import io.reactivex.rxjava3.core.Single

interface SharedRepository {
    fun isLoggedIn(): Single<Boolean>
}