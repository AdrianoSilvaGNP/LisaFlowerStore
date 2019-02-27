package com.adrianosilva.lisaflowerstore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.adrianosilva.lisaflowerstore.FLOWERS_GET_TAG
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.adrianosilva.lisaflowerstore.repository.FlowerRepository


class FlowerListViewModel internal constructor(flowerRepository: FlowerRepository) : ViewModel() {

    private val repository: FlowerRepository = flowerRepository
    private var mAllFlowers: LiveData<List<FlowerObject>> = repository.getAllFlowers()
    private val workManager: WorkManager = WorkManager.getInstance()
    internal val outputWorkInfos: LiveData<List<WorkInfo>> = workManager.getWorkInfosByTagLiveData(FLOWERS_GET_TAG)

    fun getAllFlowers() = mAllFlowers

}