package com.adrianosilva.lisaflowerstore.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adrianosilva.lisaflowerstore.database.FlowerDatabase
import com.adrianosilva.lisaflowerstore.repository.FlowerRepository

class FlowerListViewModelFactory(private val repository: FlowerRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = FlowerListViewModel(repository) as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: FlowerListViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(FlowerListViewModelFactory::class.java) {
                INSTANCE ?: FlowerListViewModelFactory(FlowerRepository.getInstance(FlowerDatabase.getInstance(application.applicationContext).flowersDao()))
                    .also { INSTANCE = it }
            }

    }
}