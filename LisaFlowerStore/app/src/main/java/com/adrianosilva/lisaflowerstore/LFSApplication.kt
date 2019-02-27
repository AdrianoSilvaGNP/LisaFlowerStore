package com.adrianosilva.lisaflowerstore

import android.app.Application
import com.parse.Parse
import tgio.parselivequery.LiveQueryClient

/**
 * Application wide class
 */
class LFSApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Parse.initialize(Parse.Configuration.Builder(this)
            .applicationId("8Kqv9M0FBsMiDUDe1iXRKA67Z7WxmdymGtm9wxxI")
            .clientKey("RwMbAlQU9i7UlYiKhEqDLtTVNwwRIMkt88QlYgCw")
            .server("https://parseapi.back4app.com")
            .build())

        LiveQueryClient.init("wss://" + "lisaflowerstore.back4app.io", "8Kqv9M0FBsMiDUDe1iXRKA67Z7WxmdymGtm9wxxI", true)
        LiveQueryClient.connect()
    }
}