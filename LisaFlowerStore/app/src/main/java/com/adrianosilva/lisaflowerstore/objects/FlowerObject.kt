package com.adrianosilva.lisaflowerstore.objects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flowers")
data class FlowerObject(
    @PrimaryKey
    val id: String,
    val name: String?,
    val description: String?,
    val price: Double?
)