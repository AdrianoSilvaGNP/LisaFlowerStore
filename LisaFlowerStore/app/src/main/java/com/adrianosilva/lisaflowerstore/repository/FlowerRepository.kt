package com.adrianosilva.lisaflowerstore.repository

import com.adrianosilva.lisaflowerstore.database.dao.FlowerDao
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import kotlin.concurrent.thread

class FlowerRepository private constructor(private val flowerDao: FlowerDao){

    fun getFlowers() = flowerDao.getAllFlowers()

    fun getFlowerByName(flowerName: String) = flowerDao.findFlowerByName(flowerName)

    fun insertFlower(flower: FlowerObject) = thread { flowerDao.insertFlower(flower) }


    companion object {
        // For Singleton instantiation
        @Volatile private var instance: FlowerRepository? = null

        fun getInstance(flowerDao: FlowerDao) =
            instance ?: synchronized(this) {
                instance ?: FlowerRepository(flowerDao).also { instance = it }
            }
    }
}