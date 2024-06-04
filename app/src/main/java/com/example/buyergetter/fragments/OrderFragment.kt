package com.example.buyergetter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buyergetter.R
import com.example.buyergetter.adapter.OrderAdapter
import com.example.buyergetter.viewmodel.OrderViewModel

class OrderFragment : Fragment() {

    private lateinit var orderAdapter: OrderAdapter
    private lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.order_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        orderAdapter = OrderAdapter(emptyList())
        recyclerView.adapter = orderAdapter

        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        orderViewModel.orders.observe(viewLifecycleOwner, { orders ->
            orders?.let { orderAdapter.setItems(it) }
        })

        return view
    }
}
