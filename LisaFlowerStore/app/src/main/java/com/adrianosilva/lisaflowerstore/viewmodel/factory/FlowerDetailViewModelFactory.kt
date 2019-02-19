package com.adrianosilva.lisaflowerstore.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adrianosilva.lisaflowerstore.repository.FlowerRepository
import com.adrianosilva.lisaflowerstore.viewmodel.FlowerDetailViewModel

class FlowerDetailViewModelFactory (private val repository: FlowerRepository, private val flowerId: Int) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FlowerDetailViewModel(repository, flowerId) as T
    }
}