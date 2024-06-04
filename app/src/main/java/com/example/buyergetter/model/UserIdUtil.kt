package com.example.buyergetter.model

import android.content.Context
import android.content.SharedPreferences
import java.util.*

object UserIdUtil {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_USER_ID = "user_id"

    fun getUserId(context: Context): String {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString(KEY_USER_ID, null)
        if (userId == null) {
            userId = UUID.randomUUID().toString()
            sharedPreferences.edit().putString(KEY_USER_ID, userId).apply()
        }
        return userId
    }
}
