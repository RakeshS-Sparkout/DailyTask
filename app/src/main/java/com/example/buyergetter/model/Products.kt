package com.example.buyergetter.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "products")
data class Products(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val image: Int,
    val name: String,
    val price: Double,
    val description: String,
    val rating: Float,
    val shopId: Int
): Parcelable
