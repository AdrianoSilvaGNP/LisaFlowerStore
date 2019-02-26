package com.adrianosilva.lisaflowerstore.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adrianosilva.lisaflowerstore.database.local.dao.FlowerDao
import com.adrianosilva.lisaflowerstore.objects.FlowerObject

/**
 * Database schema.
 */
@Database(
    entities = [FlowerObject::class],
    version = 1,
    exportSchema = false
)
abstract class FlowerDatabase : RoomDatabase() {

    abstract fun flowersDao(): FlowerDao

    companion object {
        private var INSTANCE: FlowerDatabase? = null
        private val lock = Any()
        @Synchronized
        fun getInstance(context: Context): FlowerDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FlowerDatabase::class.java, "LisaFlowerStore.db")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}