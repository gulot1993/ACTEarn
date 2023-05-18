package com.example.actearn.core

import android.content.SharedPreferences
import com.example.actearn.model.domain.User
import com.google.gson.Gson
import javax.inject.Inject

class PreferenceHelper @Inject constructor(val sharedPreferences: SharedPreferences) {
    fun setIsLoggedIn(isLoggedIn: Boolean) = sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
    fun getIsLoggedIn(): Boolean = sharedPreferences.getBoolean("isLoggedIn", false)

    fun setLoggedInUser(user: User) = sharedPreferences.edit().putString("user", Gson().toJson(user)).apply()

    fun getLoggedInUser(): User? {
        val user = sharedPreferences.getString("user", "")
        return Gson().fromJson(user, User::class.java)
    }
}