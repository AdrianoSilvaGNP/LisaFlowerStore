package com.adrianosilva.lisaflowerstore.repository

import com.adrianosilva.lisaflowerstore.database.dao.FlowerDao
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.concurrent.thread

class FlowerRepository private constructor(private val flowerDao: FlowerDao){

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun getFlowers() = flowerDao.getAllFlowers()

    fun getFlowerById(flowerId: String) = flowerDao.getFlowerById(flowerId)

    fun getFlowerByName(flowerName: String) = flowerDao.getFlowerByName(flowerName)

    fun insertFlower(flower: FlowerObject) = thread {
        // Firebase
        val myRef: DatabaseReference = database.getReference("flowers")
        myRef.child(flower.id).setValue(flower)

        // Database
        flowerDao.insertFlower(flower)
    }

    fun deleteFlowerById(flowerId: String) = thread {
        flowerDao.deleteFlowerById(flowerId)

        // Firebase
        val myRef: DatabaseReference = database.getReference("flowers")
        myRef.child(flowerId).removeValue()
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