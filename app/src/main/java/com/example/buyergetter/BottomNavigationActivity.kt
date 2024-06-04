package com.example.buyergetter

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.buyergetter.fragments.CartFragment
import com.example.buyergetter.fragments.HomeFragment
import com.example.buyergetter.fragments.OrderFragment
import com.example.buyergetter.fragments.SettingsFragment
import com.example.buyergetter.model.Shop
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class BottomNavigationActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var homeFragment: HomeFragment
    private lateinit var cartFragment: CartFragment
    private lateinit var orderFragment: OrderFragment
    private lateinit var settingsFragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigator)
        bottomNavigationView.setOnItemSelectedListener(this)
        
        val selectedShop = intent.getParcelableExtra<Shop>("selected_shop")


        homeFragment = selectedShop?.let { HomeFragment.newInstance(it) } ?: HomeFragment()
        cartFragment = CartFragment()
        orderFragment = OrderFragment()
        settingsFragment = SettingsFragment()


        loadFragment(homeFragment)
        bottomNavigationView.selectedItemId = R.id.homeId
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment = when (item.itemId) {
            R.id.homeId -> homeFragment
            R.id.cartId -> cartFragment
            R.id.orderId -> orderFragment
            R.id.settingsId -> settingsFragment
            else -> return false
        }

        loadFragment(fragment)
        return true
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.f1Fragment, fragment)
            .commit()
    }
}
