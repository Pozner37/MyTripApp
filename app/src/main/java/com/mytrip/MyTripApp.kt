package com.mytrip;

import android.app.Application
import android.content.Context
class MyTripApp: Application() {

    object Globals {
        var appContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        Globals.appContext = applicationContext
    }

}
