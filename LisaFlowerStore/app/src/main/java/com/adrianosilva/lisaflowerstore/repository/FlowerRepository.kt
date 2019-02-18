package com.adrianosilva.lisaflowerstore.repository

import com.adrianosilva.lisaflowerstore.database.dao.FlowerDao
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.concurrent.thread

class FlowerRepository private constructor(private val flowerDao: FlowerDao){

    fun getFlowers() = flowerDao.getAllFlowers()

    fun getFlowerByName(flowerName: String) = flowerDao.findFlowerByName(flowerName)

    fun insertFlower(flower: FlowerObject) = thread { flowerDao.insertFlower(flower) }

    init {
        /*val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("message")

        myRef.setValue("Hello, world!")*/
    }


    companion object {
        // For Singleton instantiation
        @Volatile private var instance: FlowerRepository? = null

        fun getInstance(flowerDao: FlowerDao) =
            instance ?: synchronized(this) {
                instance ?: FlowerRepository(flowerDao).also { instance = it }
            }
    }
}