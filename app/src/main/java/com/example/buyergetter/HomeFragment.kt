package com.example.buyergetter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buyergetter.R
import com.example.buyergetter.model.Products
import com.example.buyergetter.viewmodel.ProductAdapter

class HomeFragment : Fragment() {

    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.product_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ProductAdapter()
        recyclerView.adapter = adapter

        val productItems = listOf(
            Products(R.drawable.lap, "Laptop", "$600", "Here is brand new model", 4.2f),
            Products(R.drawable.phone, "Samsung F1", "$350", "Samsung has launched a new series", 4.0f),
            Products(R.drawable.ac, "LG Air Conditioner", "$400", "Bring a Kashmir in home", 4.1f),
            Products(R.drawable.fridge, "Fridge", "$250", "Keep your food fresh", 3.9f),
            Products(R.drawable.oven,"Microwave Oven","$75","Keep it heated",3.8f)
        )

        adapter.addItems(productItems)

        return view
    }
}
