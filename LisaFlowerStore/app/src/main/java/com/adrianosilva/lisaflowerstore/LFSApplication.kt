package com.adrianosilva.lisaflowerstore

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.provider.FirebaseInitProvider

class LFSApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}