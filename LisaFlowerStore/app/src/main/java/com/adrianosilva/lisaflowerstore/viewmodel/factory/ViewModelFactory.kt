package com.adrianosilva.lisaflowerstore.viewmodel.factory

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adrianosilva.lisaflowerstore.database.local.FlowerDatabase
import com.adrianosilva.lisaflowerstore.repository.FlowerRepository
import com.adrianosilva.lisaflowerstore.viewmodel.FlowerAddViewModel
import com.adrianosilva.lisaflowerstore.viewmodel.FlowerListViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: FlowerRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =  with(modelClass) {
        when {
            isAssignableFrom(FlowerListViewModel::class.java) -> FlowerListViewModel(
                repository
            )
            isAssignableFrom(FlowerAddViewModel::class.java) -> FlowerAddViewModel(
                repository
            )
            //isAssignableFrom(FlowerDetailViewModel::class.java) -> FlowerDetailViewModel(repository)
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(
                        FlowerRepository.getInstance(
                            FlowerDatabase.getInstance(application.applicationContext).flowersDao()
                        )
                    ).also { INSTANCE = it }
            }
    }
}