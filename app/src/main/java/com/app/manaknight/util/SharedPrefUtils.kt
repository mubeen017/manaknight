package com.app.manaknight.util

import android.content.Context
import android.content.SharedPreferences
import com.app.manaknight.util.AppController.Companion.getAppInstance
import com.app.manaknight.util.AppController.Companion.sharedPrefUtils
import com.app.manaknight.util.SharedPref.FIRST_NAME
import com.app.manaknight.util.SharedPref.LAST_NAME
import com.app.manaknight.util.SharedPref.USER_EMAIL
import com.app.manaknight.util.SharedPref.USER_TOKEN
import com.app.manaknight.util.SharedPref.myPreferences

class SharedPrefUtils {
    private val mSharedPreferences: SharedPreferences =
        getAppInstance().getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
    private var mSharedPreferencesEditor: SharedPreferences.Editor = mSharedPreferences.edit()

    init {
        mSharedPreferencesEditor.apply()
    }

    fun setValue(key: String, value: Any?) {
        when (value) {
            is Int? -> {
                mSharedPreferencesEditor.putInt(key, value!!)
                mSharedPreferencesEditor.apply()
            }
            is String? -> {
                mSharedPreferencesEditor.putString(key, value!!)
                mSharedPreferencesEditor.apply()
            }
            is Double? -> {
                mSharedPreferencesEditor.putString(key, value.toString())
                mSharedPreferencesEditor.apply()
            }
            is Long? -> {
                mSharedPreferencesEditor.putLong(key, value!!)
                mSharedPreferencesEditor.apply()
            }
            is Boolean? -> {
                mSharedPreferencesEditor.putBoolean(key, value!!)
                mSharedPreferencesEditor.apply()
            }
        }
    }

    fun getStringValue(key: String, defaultValue: String = ""): String {
        return mSharedPreferences.getString(key, defaultValue)!!
    }

    fun getIntValue(key: String, defaultValue: Int): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    fun getBooleanValue(keyFlag: String, defaultValue: Boolean = false): Boolean {
        return mSharedPreferences.getBoolean(keyFlag, defaultValue)
    }

    fun removeKey(key: String) {
        mSharedPreferencesEditor.remove(key)
        mSharedPreferencesEditor.apply()
    }

    fun clear() {
        mSharedPreferencesEditor.clear().apply()
    }

    fun clearLoginPrefs() {
        mSharedPreferencesEditor.remove(USER_TOKEN)
        mSharedPreferencesEditor.remove(USER_EMAIL)
        mSharedPreferencesEditor.remove(FIRST_NAME)
        mSharedPreferencesEditor.remove(LAST_NAME)
        mSharedPreferencesEditor.apply()
    }
}

fun getSharedPrefInstance(): SharedPrefUtils {
    return if (sharedPrefUtils == null) {
        sharedPrefUtils = SharedPrefUtils()
        sharedPrefUtils!!
    } else {
        sharedPrefUtils!!
    }
}