package com.arjunpscwala.pscwala

import android.app.Activity
import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

actual object AppContext {
    private lateinit var application: Application
    var currentActivity: WeakReference<Activity>? = null


    fun setUp(context: Context) {
        application = context as Application
    }

    fun get(): Context {
        if (::application.isInitialized.not()) throw Exception("Application context isn't initialized")
        return application.applicationContext
    }
}