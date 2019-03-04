package com.adrianosilva.lisaflowerstore.database.remote

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.adrianosilva.lisaflowerstore.database.local.FlowerDatabase
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.parse.ParseObject
import com.parse.ParseQuery
import org.joda.time.DateTime
import kotlin.concurrent.thread

class GetAllFlowersFromCloudWorker(context: Context, params: WorkerParameters): Worker(context, params) {

    private val TAG by lazy { GetAllFlowersFromCloudWorker::class.java.simpleName }

    private val roomDatabase = FlowerDatabase.getInstance(applicationContext)
    private val flowers = mutableListOf<FlowerObject>()

    override fun doWork(): Result {

        return try {
            val query = ParseQuery.getQuery<ParseObject>("FlowerObject")
            query.findInBackground { objects, e ->
                if (e == null) {
                    for (i: Int in 0 until objects.size) {
                        val currentFlower = FlowerObject(
                            objects[i].getString("localId")!!,
                            objects[i].getString("name")!!,
                            objects[i].getString("description")!!,
                            objects[i].getDouble("price"),
                            DateTime(objects[i].getDate("updatedAt")),
                            DateTime(objects[i].getDate("createdAt"))
                        )
                        flowers.add(currentFlower)
                    }
                    thread { roomDatabase.flowersDao().insertBulkFlowers(flowers) }
                } else {
                    Log.w(TAG, e)
                }
            }
            return Result.success()
        } catch (throwable: Throwable) {
            Log.w(TAG, throwable)
            Result.failure()
        }
    }
}