package com.example.buyergetter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buyergetter.R
import com.example.buyergetter.model.CartItem
import com.example.buyergetter.model.Order
import com.example.buyergetter.model.UserIdUtil
import com.example.buyergetter.adapter.CartAdapter
import com.example.buyergetter.viewmodel.CartViewModel
import com.example.buyergetter.viewmodel.OrderViewModel
import com.google.firebase.database.FirebaseDatabase

class CartFragment : Fragment() {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartViewModel: CartViewModel
    private lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.cart_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        cartAdapter = CartAdapter(listOf(), { cartItem -> onPlaceOrderClick(cartItem) }, { cartItem -> onRemoveClick(cartItem) })
        recyclerView.adapter = cartAdapter

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        cartViewModel.cartItems.observe(viewLifecycleOwner, { items ->
            items?.let { cartAdapter.setItems(it) }
        })

        return view
    }

    private fun onPlaceOrderClick(cartItem: CartItem) {
        val userId = UserIdUtil.getUserId(requireContext())

        val order = Order(
            orderId = generateOrderId(),
            userId = userId,
            items = listOf(
                Order.OrderItem(
                    name = cartItem.name,
                    image = cartItem.image,
                    quantity = cartItem.quantity,
                    amount = cartItem.amount
                )
            ),
            totalAmount = cartItem.amount,
            orderDate = System.currentTimeMillis()
        )

        val database = FirebaseDatabase.getInstance().reference
        val ordersRef = database.child("orders").push()
        ordersRef.setValue(order).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                cartViewModel.removeCartItem(cartItem)
                Toast.makeText(context, "Order placed and item removed from cart", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to place order", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onRemoveClick(cartItem: CartItem) {
        cartViewModel.removeCartItem(cartItem)
    }

    private fun generateOrderId(): String {
        return FirebaseDatabase.getInstance().reference.push().key ?: ""
    }
}
