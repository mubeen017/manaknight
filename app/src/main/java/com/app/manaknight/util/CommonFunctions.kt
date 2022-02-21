package com.app.manaknight.util

import androidx.fragment.app.FragmentActivity

fun isLoggedIn(): Boolean = getSharedPrefInstance().getBooleanValue(SharedPref.IS_LOGGED_IN)
fun getUserToken(): String = getSharedPrefInstance().getStringValue(SharedPref.USER_TOKEN)
fun getUserEmail(): String = getSharedPrefInstance().getStringValue(SharedPref.USER_EMAIL)
fun userFirstName(): String = getSharedPrefInstance().getStringValue(SharedPref.FIRST_NAME)
fun userLastName(): String = getSharedPrefInstance().getStringValue(SharedPref.LAST_NAME)

fun goBack(activity: FragmentActivity) {
    if (activity.supportFragmentManager.backStackEntryCount > 0) {
        activity.supportFragmentManager.popBackStackImmediate()
    } else {
        activity.onBackPressed()
    }
}
