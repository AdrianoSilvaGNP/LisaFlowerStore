package com.adrianosilva.lisaflowerstore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.adrianosilva.lisaflowerstore.repository.FlowerRepository


class FlowerListViewModel internal constructor(flowerRepository: FlowerRepository) : ViewModel() {

    private val repository: FlowerRepository = flowerRepository
    private var mAllFlowers: LiveData<List<FlowerObject>> = repository.getFlowers()

    fun getAllFlowers() = mAllFlowers

}