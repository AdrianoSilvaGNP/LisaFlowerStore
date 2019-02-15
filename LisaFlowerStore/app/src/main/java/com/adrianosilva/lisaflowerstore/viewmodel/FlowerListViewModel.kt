package com.adrianosilva.lisaflowerstore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.adrianosilva.lisaflowerstore.repository.FlowerRepository

class FlowerListViewModel internal constructor(flowerRepository: FlowerRepository) : ViewModel() {

    private var mAllFlowers: LiveData<List<FlowerObject>> = flowerRepository.getFlowers()

    fun getAllFlowers() = mAllFlowers

}