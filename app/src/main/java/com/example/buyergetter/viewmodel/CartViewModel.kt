package com.example.buyergetter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.buyergetter.model.AppDatabase
import com.example.buyergetter.model.CartItem
import com.example.buyergetter.repository.MyRepository
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MyRepository
    val allCartItems: LiveData<List<CartItem>>

    init {
        val cartDao = AppDatabase.getDatabase(application).cartDao()
        repository = MyRepository(cartDao)
        allCartItems = repository.allCartItems
    }

    fun deleteCartItemById(cartItemId: Int) = viewModelScope.launch {
        repository.deleteCartItemById(cartItemId)
    }
}
