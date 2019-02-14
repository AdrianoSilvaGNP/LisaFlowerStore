package com.adrianosilva.lisaflowerstore.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
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
        private var instance: FlowerDatabase? = null
        @Synchronized
        fun get(context: Context): FlowerDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlowerDatabase::class.java, "LisaFlowerStore"
                ).build()
            }
            return instance!!
        }
    }
}