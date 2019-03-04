package com.adrianosilva.lisaflowerstore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.adrianosilva.lisaflowerstore.repository.FlowerRepository

class FlowerDetailViewModel internal constructor(flowerRepository: FlowerRepository, private val flowerId: String): ViewModel() {

    private val repository: FlowerRepository = flowerRepository
    var flower: LiveData<FlowerObject> = repository.getFlowerById(flowerId)

    fun deleteFlower(): Boolean {
        return repository.deleteFlowerById(flowerId)
    }

}