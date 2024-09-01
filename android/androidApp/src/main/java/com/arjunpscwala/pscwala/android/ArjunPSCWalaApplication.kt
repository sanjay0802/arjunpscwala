package com.arjunpscwala.pscwala.android

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.arjunpscwala.pscwala.AppContext
import java.lang.ref.WeakReference

class ArjunPSCWalaApplication : Application(), Application.ActivityLifecycleCallbacks {
    companion object {
        private var currentActivity: WeakReference<Activity>? = null
    }


    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        currentActivity = WeakReference(activity);

    }

    override fun onActivityStarted(activity: Activity) {

        AppContext.currentActivity = WeakReference(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        AppContext.currentActivity = WeakReference(activity)
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}