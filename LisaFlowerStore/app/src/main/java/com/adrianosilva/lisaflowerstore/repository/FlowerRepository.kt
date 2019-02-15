package com.adrianosilva.lisaflowerstore.repository

import com.adrianosilva.lisaflowerstore.database.dao.FlowerDao

class FlowerRepository private constructor(private val flowerDao: FlowerDao){

    fun getFlowers() = flowerDao.getAllFlowers()

    fun getFlowerByName(flowerName: String) = flowerDao.findFlowerByName(flowerName)


    companion object {
        // For Singleton instantiation
        @Volatile private var instance: FlowerRepository? = null

        fun getInstance(flowerDao: FlowerDao) =
            instance ?: synchronized(this) {
                instance ?: FlowerRepository(flowerDao).also { instance = it }
            }
    }
}