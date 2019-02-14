package com.adrianosilva.lisaflowerstore.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.adrianosilva.lisaflowerstore.objects.FlowerObject

@Dao
interface FlowerDao {

    @Query("SELECT * FROM flowers")
    fun getAllFlowers(): LiveData<List<FlowerObject>>

    @Query("SELECT * FROM flowers WHERE name = :name")
    fun findFlowerByName(name: String): LiveData<FlowerObject>

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(flowers: LiveData<List<FlowerObject>>)*/

    /*@Update
    fun updateFlower(flower: LiveData<FlowerObject>)

    @Delete
    fun deleteFlower(flower : FlowerObject)*/
}