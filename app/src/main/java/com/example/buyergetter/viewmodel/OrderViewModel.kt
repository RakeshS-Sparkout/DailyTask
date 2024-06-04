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
            }
        })
    }

    fun placeOrder(order: Order) {
        val database = FirebaseDatabase.getInstance().reference
        val ordersRef = database.child("orders").push()
        order.orderId = ordersRef.key ?: throw IllegalStateException("Failed to generate order ID")

        ordersRef.setValue(order).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "Order Placed Successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext,"Order not placed",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun updateOrderStatus(orderId: String, newStatus: Int) {
        val database = FirebaseDatabase.getInstance().reference
        val orderRef = database.child("orders").child(orderId).child("status")

        orderRef.setValue(newStatus).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "Order Status Updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Failed to Update Order Status", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
