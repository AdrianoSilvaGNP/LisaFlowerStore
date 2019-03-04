package com.adrianosilva.lisaflowerstore

import android.app.Application
import android.content.Context
import com.parse.Parse


/**
 * Application wide class
 */
class LFSApplication: Application() {

    private lateinit var appContext: Context

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        Parse.initialize(Parse.Configuration.Builder(this)
            .applicationId("8Kqv9M0FBsMiDUDe1iXRKA67Z7WxmdymGtm9wxxI")
            .clientKey("RwMbAlQU9i7UlYiKhEqDLtTVNwwRIMkt88QlYgCw")
            .server("https://parseapi.back4app.com")
            .build())
    }

    fun getAppContext(): Context {
        return appContext
    }
}