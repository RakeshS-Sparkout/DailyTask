package com.example.buyergetter.model

import androidx.room.PrimaryKey

data class Shop(
    @PrimaryKey(autoGenerate = true) val shopId: Int = 0,
    val shopName: String,
    val address: String,
    val rating: Float,
    val photo: Int,
    val description: String
)
