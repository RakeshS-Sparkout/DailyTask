package com.example.buyergetter.repository

import androidx.lifecycle.LiveData
import com.example.buyergetter.model.Products
import com.example.buyergetter.model.ProductDao

class ProductRepository(private val productDao: ProductDao) {

    fun getProductsForShop(shopId: Int): LiveData<List<Products>> {
        return productDao.getProductsForShop(shopId)
    }

    suspend fun insertProducts(products: List<Products>) {
        productDao.insertAll(products)
    }
}

