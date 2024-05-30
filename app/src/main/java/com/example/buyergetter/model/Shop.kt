package com.example.buyergetter.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "shops")
data class Shop(
    @PrimaryKey(autoGenerate = true) val shopId: Int = 0,
    val shopName: String,
    val address: String,
    val rating: Float,
    val photo: Int,
    val description: String
) : Parcelable
