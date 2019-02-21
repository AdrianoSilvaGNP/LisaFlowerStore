package com.adrianosilva.lisaflowerstore.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.adrianosilva.lisaflowerstore.objects.FlowerObject

@Dao
interface FlowerDao {

    @Query("SELECT * FROM flowers ORDER BY name ASC")
    fun getAllFlowers(): LiveData<List<FlowerObject>>

    @Query("SELECT * FROM flowers WHERE name = :name")
    fun getFlowerByName(name: String): LiveData<FlowerObject>

    @Query("SELECT * FROM flowers WHERE name = :name")
    fun getFlowerByNameSync(name: String): FlowerObject

    @Query("SELECT * FROM flowers WHERE id = :id")
    fun getFlowerById(id: String): LiveData<FlowerObject>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(flowers: List<FlowerObject>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFlower(flower: FlowerObject)

    @Update
    fun updateFlower(flower: FlowerObject)

    @Query("DELETE FROM flowers WHERE id = :id")
    fun deleteFlowerById(id: String)
}