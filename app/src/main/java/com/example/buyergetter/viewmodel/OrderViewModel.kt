package com.example.buyergetter.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.buyergetter.model.Order
import com.google.firebase.database.*

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val applicationContext = application.applicationContext

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    init {
        fetchOrdersFromFirebase()
    }

    private fun fetchOrdersFromFirebase() {
        val database = FirebaseDatabase.getInstance().reference
        val ordersRef = database.child("orders")

        ordersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orders = mutableListOf<Order>()
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(Order::class.java)
                    if (order != null) {
                        orders.add(order)
                    }
                }
                _orders.value = orders
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors with Firebase operations
            }
        })
    }

    fun placeOrder(order: Order) {
        val database = FirebaseDatabase.getInstance().reference
        val ordersRef = database.child("orders").push() // Get a reference for a new order
        order.orderId = ordersRef.key ?: throw IllegalStateException("Failed to generate order ID") // Set order ID

        ordersRef.setValue(order).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // This is where you can handle post-order placement tasks
                // For example: Update UI to reflect the order has been placed successfully
                Toast.makeText(applicationContext, "Order Placed Successfully", Toast.LENGTH_LONG).show()
            } else {
                // Handle the failure of placing an order, could log an error or notify the user
                // For example: Use a Snackbar to show an error message
            }
        }
    }
}
