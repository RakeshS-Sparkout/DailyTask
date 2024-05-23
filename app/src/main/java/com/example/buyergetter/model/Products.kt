package com.example.buyergetter.model

import androidx.room.Entity

@Entity(tableName = "products")
data class Products(
    val image: Int,
    val name: String,
    val price: String,
    val description: String,
    val rating: Float
)
