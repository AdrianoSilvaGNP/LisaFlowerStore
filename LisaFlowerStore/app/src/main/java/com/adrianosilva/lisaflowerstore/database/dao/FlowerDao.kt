package com.adrianosilva.lisaflowerstore.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.adrianosilva.lisaflowerstore.objects.FlowerObject

@Dao
interface FlowerDao {

    @Query("SELECT * FROM flowers ORDER BY id ASC")
    fun getAllFlowers(): LiveData<List<FlowerObject>>

    @Query("SELECT * FROM flowers WHERE name = :name")
    fun findFlowerByName(name: String): LiveData<FlowerObject>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(flowers: List<FlowerObject>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFlower(flower: FlowerObject)

    @Update
    fun updateFlower(flower: FlowerObject)

    @Delete
    fun deleteFlower(flower : FlowerObject)
}