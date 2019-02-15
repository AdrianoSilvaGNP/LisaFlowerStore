package com.adrianosilva.lisaflowerstore.objects

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flowers")
data class FlowerObject(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int,
    val name: String?,
    val description: String?,
    val price: Double?
)