package com.example.actearn.repository

import com.example.actearn.core.PreferenceHelper
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SharedRepositoryImpl @Inject constructor(
    val preferenceHelper: PreferenceHelper
): SharedRepository {
    override fun isLoggedIn(): Single<Boolean> {
        return Single.just(preferenceHelper.getIsLoggedIn())
    }
}