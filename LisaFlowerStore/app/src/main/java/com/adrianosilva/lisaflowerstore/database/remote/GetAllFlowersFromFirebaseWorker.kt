package com.adrianosilva.lisaflowerstore.database.remote

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.adrianosilva.lisaflowerstore.database.local.FlowerDatabase
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.google.firebase.database.*

class GetAllFlowersFromFirebaseWorker(context: Context, params: WorkerParameters): Worker(context, params) {

    private val fbDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val roomDatabase = FlowerDatabase.getInstance(applicationContext)

    override fun doWork(): Result {


        return try {
            val myRef: DatabaseReference = fbDatabase.getReference("flowers")
            val flowers: MutableList<FlowerObject> = mutableListOf()

            myRef.addListenerForSingleValueEvent(object: ValueEventListener {

                override fun onDataChange(p0: DataSnapshot) {
                    for (flowersSnapshot in p0.children) {
                        val flower: FlowerObject = flowersSnapshot.getValue(FlowerObject::class.java)!!
                        flowers.add(flower)

                    }
                }

                override fun onCancelled(p0: DatabaseError) {

                }

            })

            roomDatabase.flowersDao().insertAll(flowers)
            return Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }

        

    }

}