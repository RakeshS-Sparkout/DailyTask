package com.example.buyergetter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buyergetter.repository.CartAdapter
import com.example.buyergetter.viewmodel.CartViewModel

class OrderFragment : Fragment() {

    private lateinit var adapter: CartAdapter
    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.order_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CartAdapter { cartItemId -> deleteCartItem(cartItemId) }
        recyclerView.adapter = adapter

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        cartViewModel.allCartItems.observe(viewLifecycleOwner, { cartItems ->
            cartItems?.let { adapter.setItems(it) }
        })

        return view
    }

    private fun deleteCartItem(cartItemId: Int) {
        cartViewModel.deleteCartItemById(cartItemId)
    }
}
