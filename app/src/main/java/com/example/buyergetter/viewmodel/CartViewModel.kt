package com.example.buyergetter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.buyergetter.model.AppDatabase
import com.example.buyergetter.model.CartItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val db: AppDatabase = AppDatabase.getDatabase(application)
    val cartItems: LiveData<List<CartItem>> = db.cartDao().getAllCartItems()

    fun removeCartItem(cartItem: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            db.cartDao().deleteCartItemById(cartItem.id)
        }
    }
}
