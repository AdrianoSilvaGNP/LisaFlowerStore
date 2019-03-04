package com.adrianosilva.lisaflowerstore.objects

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.adrianosilva.lisaflowerstore.database.local.converters.DateTimeConverter
import org.joda.time.DateTime

@Entity(tableName = "flowers")
@TypeConverters(DateTimeConverter::class)
data class FlowerObject(
    @NonNull
    @PrimaryKey
    val localId: String,
    val name: String,
    val description: String,
    val price: Double,
    val updatedAt: DateTime,
    val createdAt: DateTime
)