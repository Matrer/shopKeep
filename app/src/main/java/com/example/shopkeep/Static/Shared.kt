package com.example.shopkeep.Static

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences

object Shared {
    val PREFS_NAME = "MyPrefsFile"
    private val PREF_USERNAME = "username"
    private val PREF_PASSWORD = "password"

    fun save(username:String, password:String,context:Context)
    {

        context.getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME, username)
                .putString(PREF_PASSWORD, password)
                .apply()
    }
    fun getPassword(context:Context):String
    {
        val pref = context.getSharedPreferences(PREFS_NAME,MODE_PRIVATE).getString(PREF_PASSWORD, null)
        return pref?:""
    }
    fun getUserName(context:Context):String
    {
        val pref = context.getSharedPreferences(PREFS_NAME,MODE_PRIVATE).getString(PREF_USERNAME, null)
        return pref?:""
    }
}
