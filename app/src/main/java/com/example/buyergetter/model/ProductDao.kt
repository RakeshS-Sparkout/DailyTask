package com.example.buyergetter.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Products>>
    @Query("SELECT * FROM products WHERE shopId = :shopId")
    fun getProductsForShop(shopId: Int): LiveData<List<Products>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Products>)
}
