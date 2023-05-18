package com.example.actearn.core

import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceHelper @Inject constructor(val sharedPreferences: SharedPreferences) {
    fun setIsLoggedIn(isLoggedIn: Boolean) = sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn)
    fun getIsLoggedIn(): Boolean = sharedPreferences.getBoolean("isLoggedIn", false)
}