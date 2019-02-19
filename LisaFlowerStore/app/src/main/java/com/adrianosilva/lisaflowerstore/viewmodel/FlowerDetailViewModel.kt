package com.adrianosilva.lisaflowerstore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.adrianosilva.lisaflowerstore.repository.FlowerRepository

class FlowerDetailViewModel internal constructor(flowerRepository: FlowerRepository, private val flowerId: Int): ViewModel() {

    private val repository: FlowerRepository = flowerRepository
    var flower: LiveData<FlowerObject>

    init {
        flower = repository.getFlowerById(flowerId)
    }

}