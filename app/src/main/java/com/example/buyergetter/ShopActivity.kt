package com.example.buyergetter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buyergetter.model.Shop
import com.example.buyergetter.adapter.ShopAdapter

class ShopActivity : AppCompatActivity() {

    private lateinit var adapter: ShopAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView: RecyclerView = findViewById(R.id.shop_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ShopAdapter { shop -> onShopClick(shop) }
        recyclerView.adapter = adapter

        val shops = listOf(
            Shop(
                111,
                "Supreme Mobiles",
                "Ganapathi",
                4.2f,
                R.drawable.supreme,
                "Mobile Destination"
            ),
            Shop(
                222,
                "Poorvika Mobiles",
                "Saravanampatti",
                3.6f,
                R.drawable.poorvika,
                "Tiny World"
            ),
            Shop(
                333,
                "The Chennai Mobiles",
                "R.S.Puram",
                3.9f,
                R.drawable.chennai_mobiles,
                "Part of Hands"
            ),
            Shop(
                444,
                "Univercell Mobiles",
                "Hopes",
                4.0f,
                R.drawable.univercell,
                "Universal Connect"
            )
        )

        adapter.addItems(shops)
    }

    private fun onShopClick(shop: Shop) {
        val intent = Intent(this, BottomNavigationActivity::class.java).apply {
            putExtra("selected_shop", shop)
        }
        startActivity(intent)
    }
}
