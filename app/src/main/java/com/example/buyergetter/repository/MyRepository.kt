package com.example.buyergetter.repository

import androidx.lifecycle.LiveData
import com.example.buyergetter.model.CartItem
import com.example.buyergetter.model.CartDao

class MyRepository(private val cartDao: CartDao) {

    val allCartItems: LiveData<List<CartItem>> = cartDao.getAllCartItems()

    suspend fun insert(cartItem: CartItem) {
        cartDao.insert(cartItem)
    }

    suspend fun deleteCartItemById(cartItemId: Int) {
        cartDao.deleteCartItemById(cartItemId)
    }
}
