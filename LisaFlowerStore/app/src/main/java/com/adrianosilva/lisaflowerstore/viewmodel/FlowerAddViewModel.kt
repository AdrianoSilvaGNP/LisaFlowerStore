package com.adrianosilva.lisaflowerstore.viewmodel

import androidx.lifecycle.ViewModel
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.adrianosilva.lisaflowerstore.repository.FlowerRepository

class FlowerAddViewModel internal constructor(flowerRepository: FlowerRepository): ViewModel() {

    private val repository: FlowerRepository = flowerRepository

    fun insertFlower(flower: FlowerObject) = repository.insertFlower(flower)

}