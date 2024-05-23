package com.example.buyergetter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buyergetter.model.AppDatabase
import com.example.buyergetter.repository.CartAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderFragment : Fragment() {

    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.order_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CartAdapter { cartItemId -> deleteCartItem(cartItemId) }
        recyclerView.adapter = adapter

        loadCartItems()

        return view
    }

    private fun loadCartItems() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(requireContext())
            val cartItems = db.cartDao().getAllCartItems()
            withContext(Dispatchers.Main) {
                adapter.addItems(cartItems)
            }
        }
    }

    private fun deleteCartItem(cartItemId: Int) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(requireContext())
            db.cartDao().deleteCartItemById(cartItemId)
            loadCartItems()
        }
    }
}
