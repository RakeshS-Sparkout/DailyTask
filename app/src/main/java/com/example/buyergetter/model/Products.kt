package com.example.buyergetter.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Entity(tableName = "products")
data class Products(
    val image: Int,
    val name: String,
    val price: Int,
    val description: String,
    val rating: Float
)

@Dao
interface ProductDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Products)

    @Query("SELECT * FROM products" )
    fun getAllProducts(): LiveData<List<Products>>

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductById(productId: Int): Products?

    @Update
    suspend fun updateProduct(product: Products)

    @Delete
    suspend fun deleteProduct(product: Products)

}

