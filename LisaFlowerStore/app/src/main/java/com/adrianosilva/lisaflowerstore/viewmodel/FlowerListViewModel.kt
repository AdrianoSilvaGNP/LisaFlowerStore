package com.adrianosilva.lisaflowerstore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.adrianosilva.lisaflowerstore.repository.FlowerRepository


class FlowerListViewModel internal constructor(flowerRepository: FlowerRepository) : ViewModel() {

    private var mAllFlowers: LiveData<List<FlowerObject>> = MutableLiveData<List<FlowerObject>>()
    private val repository: FlowerRepository = flowerRepository

    init {
        mAllFlowers = repository.getFlowers()
    }

    fun getAllFlowers() = mAllFlowers

}