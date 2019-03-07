package com.adrianosilva.lisaflowerstore.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.adrianosilva.lisaflowerstore.repository.FlowerRepository
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class FlowerAddViewModel internal constructor(flowerRepository: FlowerRepository): ViewModel() {

    private val repository: FlowerRepository = flowerRepository

    fun insertFlower(flower: FlowerObject, context: Context) {
        doAsync {
            if (hasActiveInternetConnection())
            repository.insertFlower(flower)
        else
            uiThread { Toast.makeText(context, "No internet connection. Cannot add.", Toast.LENGTH_SHORT).show() }
        }
    }

    private fun hasActiveInternetConnection(): Boolean {

        return try {
            val urlc = URL("http://www.google.com").openConnection() as HttpURLConnection
            urlc.setRequestProperty("User-Agent", "Test")
            urlc.setRequestProperty("Connection", "close")
            urlc.connectTimeout = 1500
            urlc.connect()
            (urlc.responseCode == 200)
        } catch (e: IOException) {
            Log.e("FlowerDetailViewModel", "Error checking internet connection", e)
            false
        }
    }
}