package com.example.buyergetter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.buyergetter.R
import com.example.buyergetter.model.AppDatabase
import com.example.buyergetter.model.Products
import com.example.buyergetter.repository.ProductRepository

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val productDao = AppDatabase.getDatabase(application).productDao()
    private val productRepository: ProductRepository = ProductRepository(productDao)

    private val _products = MutableLiveData<List<Products>>()
    val products: LiveData<List<Products>> = _products

    fun getProductsForShop(shopId: Int): LiveData<List<Products>> {

        val productList = when (shopId) {
            111 -> listOf(
                Products(
                    1,
                    R.drawable.lap,
                    "Laptop",
                    600.0,
                    "Here is a brand new model",
                    4.4f,
                    shopId
                ),
                Products(
                    2,
                    R.drawable.phone,
                    "Mobile Phone",
                    350.0,
                    "Here is a brand new model",
                    4.0f,
                    shopId
                ),
                Products(
                    1,
                    R.drawable.lap,
                    "Laptop",
                    600.0,
                    "Here is a brand new model",
                    4.4f,
                    shopId
                ),
                Products(
                    2,
                    R.drawable.phone,
                    "Mobile Phone",
                    350.0,
                    "Here is a brand new model",
                    4.0f,
                    shopId
                )
            )

            222 -> listOf(
                Products(
                    3,
                    R.drawable.ac,
                    "Air Conditioner",
                    400.0,
                    "Here is a brand new model",
                    3.9f,
                    shopId
                ),
                Products(
                    4,
                    R.drawable.fridge,
                    "Refrigerator",
                    250.0,
                    "Here is a brand new model",
                    3.7f,
                    shopId
                ),
                Products(
                    3,
                    R.drawable.ac,
                    "Air Conditioner",
                    400.0,
                    "Here is a brand new model",
                    3.9f,
                    shopId
                ),
                Products(
                    4,
                    R.drawable.fridge,
                    "Refrigerator",
                    250.0,
                    "Here is a brand new model",
                    3.7f,
                    shopId
                )
            )

            333 -> listOf(
                Products(
                    5,
                    R.drawable.oven,
                    "Microwave Oven",
                    75.0,
                    "Here is a brand new model",
                    3.5f,
                    shopId
                ),
                Products(
                    6,
                    R.drawable.headphone,
                    "Headphone",
                    50.0,
                    "Here is a brand new model",
                    3.8f,
                    shopId
                ),
                Products(
                    5,
                    R.drawable.oven,
                    "Microwave Oven",
                    75.0,
                    "Here is a brand new model",
                    3.5f,
                    shopId
                ),
                Products(
                    6,
                    R.drawable.headphone,
                    "Headphone",
                    50.0,
                    "Here is a brand new model",
                    3.8f,
                    shopId
                )
            )

            444 -> listOf(
                Products(
                    7,
                    R.drawable.airpods,
                    "Airpods",
                    60.0,
                    "Here is brand new model",
                    3.9f,
                    shopId
                ),
                Products(
                    8,
                    R.drawable.television,
                    "Television",
                    500.00,
                    "Here is brand new model",
                    4.5f,
                    shopId
                ),
                Products(
                    7,
                    R.drawable.airpods,
                    "Airpods",
                    60.0,
                    "Here is brand new model",
                    3.9f,
                    shopId
                ),
                Products(
                    8,
                    R.drawable.television,
                    "Television",
                    500.00,
                    "Here is brand new model",
                    4.5f,
                    shopId
                )
            )

            else -> emptyList()
        }

        val liveData = MutableLiveData<List<Products>>()
        liveData.value = productList
        return liveData
    }
}
