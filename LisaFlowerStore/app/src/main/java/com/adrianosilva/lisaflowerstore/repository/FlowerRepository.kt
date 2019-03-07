package com.adrianosilva.lisaflowerstore.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.work.*
import com.adrianosilva.lisaflowerstore.FLOWERS_GET_TAG
import com.adrianosilva.lisaflowerstore.FLOWERS_GET_WORK_NAME
import com.adrianosilva.lisaflowerstore.database.local.dao.FlowerDao
import com.adrianosilva.lisaflowerstore.database.remote.GetAllFlowersFromCloudWorker
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.adrianosilva.lisaflowerstore.ui.flower.FlowerDetailFragment
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.livequery.ParseLiveQueryClient
import com.parse.livequery.SubscriptionHandling
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.joda.time.DateTime
import java.io.IOException
import java.net.URI
import java.time.Duration

import kotlin.concurrent.thread

class FlowerRepository private constructor(private val flowerDao: FlowerDao){

    private val TAG by lazy { FlowerRepository::class.java.simpleName }

    private val workManager: WorkManager = WorkManager.getInstance()
    private val parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient(URI("wss://lisaflowerstore.back4app.io"))


    init {
        val parseQuery: ParseQuery<ParseObject> = ParseQuery.getQuery("FlowerObject")
        val subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery)

        subscriptionHandling.handleEvents { query, event, `object` ->

            when (event) {
                SubscriptionHandling.Event.CREATE -> {
                    Log.w(TAG, query.className + `object`.objectId)
                    val flowerFromCloud = FlowerObject(
                        `object`.getString("localId")!!,
                        `object`.getString("name")!!,
                        `object`.getString("description")!!,
                        `object`.getDouble("price"),
                        DateTime(`object`.getDate("updatedAt")),
                        DateTime(`object`.getDate("createdAt"))
                    )
                    thread { flowerDao.insertFlower(flowerFromCloud) }

                }
                SubscriptionHandling.Event.DELETE -> thread { flowerDao.deleteFlowerById(`object`.getString("localId")!!) }
                SubscriptionHandling.Event.UPDATE -> {

                }
                else -> {}
            }
        }
    }

    fun getAllFlowers(): LiveData<List<FlowerObject>> {

        getAllFlowersFromCloud()

        return flowerDao.getAllFlowers()
    }

    fun getAllFlowersFromCloud() {
        val loadFlowersConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val loadFlowersRequest = OneTimeWorkRequestBuilder<GetAllFlowersFromCloudWorker>()
            .setConstraints(loadFlowersConstraints)
            .addTag(FLOWERS_GET_TAG)
            .build()

        workManager.enqueueUniqueWork(FLOWERS_GET_WORK_NAME, ExistingWorkPolicy.KEEP, loadFlowersRequest)
    }

    fun getFlowerById(flowerId: String) = flowerDao.getFlowerById(flowerId)

    fun getFlowerByName(flowerName: String) = flowerDao.getFlowerByName(flowerName)

    fun insertFlower(flower: FlowerObject) {

        val flowerToCloud = ParseObject("FlowerObject")
        flowerToCloud.put("localId", flower.localId)
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

    fun deleteFlowerById(flowerId: String) {

        val query = ParseQuery.getQuery<ParseObject>("FlowerObject")
        query.whereEqualTo("localId", flowerId).getFirstInBackground { entity, e ->
            if (e == null) {
                entity.deleteInBackground {e ->
                    if (e == null) {
                        // successful delete on server
                        // delete in DB
                        thread { flowerDao.deleteFlowerById(flowerId) }
                    } else {

                    }
                }
            }
        }
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