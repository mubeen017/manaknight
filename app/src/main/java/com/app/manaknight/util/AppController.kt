package com.app.manaknight.util

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class AppController : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        appInstance = this
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        private lateinit var appInstance: AppController
        var sharedPrefUtils: SharedPrefUtils? = null

        fun getAppInstance(): AppController {
            return appInstance
        }
    }

}
