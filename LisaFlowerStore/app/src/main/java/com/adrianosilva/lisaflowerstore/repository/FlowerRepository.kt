package com.adrianosilva.lisaflowerstore.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.*
import com.adrianosilva.lisaflowerstore.FLOWERS_GET_TAG
import com.adrianosilva.lisaflowerstore.FLOWERS_GET_WORK_NAME
import com.adrianosilva.lisaflowerstore.database.local.dao.FlowerDao
import com.adrianosilva.lisaflowerstore.database.remote.GetAllFlowersFromCloudWorker
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.livequery.ParseLiveQueryClient

import kotlin.concurrent.thread

class FlowerRepository private constructor(private val flowerDao: FlowerDao){

    private val workManager: WorkManager = WorkManager.getInstance()
    val parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient()

    val parseQuery: ParseQuery<ParseObject> = ParseQuery.getQuery("FlowerObject")

    /*private val sub = BaseQuery.Builder("FlowerObject")
        .build().subscribe()


    init {
        sub.on(LiveQueryEvent.CREATE) {

            val flower = FlowerObject(it.getString("id"),
                it.getString("name"),
                it.getString("description"),
                it.getDouble("price"),
                DateTime(it.getLong("updatedAt")),
                DateTime(it.getLong("createdAt")))

            flowerDao.insertFlower(flower)
        }
    }*/

    fun getAllFlowers(): LiveData<List<FlowerObject>> {

        val loadFlowersConstraints = Constraints.Builder()
               .setRequiredNetworkType(NetworkType.CONNECTED)
               .build()


           val loadFlowersRequest = OneTimeWorkRequestBuilder<GetAllFlowersFromCloudWorker>()
               .setConstraints(loadFlowersConstraints)
               .addTag(FLOWERS_GET_TAG)
               .build()

           workManager.enqueueUniqueWork(FLOWERS_GET_WORK_NAME, ExistingWorkPolicy.KEEP, loadFlowersRequest)

        return flowerDao.getAllFlowers()
    }

    fun getFlowerById(flowerId: String) = flowerDao.getFlowerById(flowerId)

    fun getFlowerByName(flowerName: String) = flowerDao.getFlowerByName(flowerName)

    fun insertFlower(flower: FlowerObject) {

        val flowerToCloud = ParseObject("FlowerObject")
        flowerToCloud.put("id", flower.id)
        flowerToCloud.put("updatedAt", flower.updatedAt.toDate())
        flowerToCloud.put("createdAt", flower.createdAt.toDate())
        flowerToCloud.put("name", flower.name)
        flowerToCloud.put("description", flower.description)
        flowerToCloud.put("price", flower.price)

        flowerToCloud.saveInBackground {
            // Here you can handle errors, if thrown. Otherwise, "e" should be null
            if (it == null) {
                // Success
                // Database
                thread { flowerDao.insertFlower(flower) }
            } else {
                // Error
                Log.w("FlowerToCloud", it.toString())
            }

        }
    }

    fun deleteFlowerById(flowerId: String) = thread {
        flowerDao.deleteFlowerById(flowerId)

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