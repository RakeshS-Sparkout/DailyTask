package com.example.buyergetter.repository

import androidx.lifecycle.LiveData
import com.example.buyergetter.model.CartDao
import com.example.buyergetter.model.CartItem

class MyRepository(private val cartDao: CartDao) {

    val allCartItems: LiveData<List<CartItem>> = cartDao.getAllCartItems()

    suspend fun insert(cartItem: CartItem) {
        cartDao.insert(cartItem)
    }

    suspend fun deleteCartItemById(cartItemId: Int) {
        cartDao.deleteCartItemById(cartItemId)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}
