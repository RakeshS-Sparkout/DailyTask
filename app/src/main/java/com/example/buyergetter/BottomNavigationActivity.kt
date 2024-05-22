package com.example.buyergetter

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class BottomNavigationActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_bottom_nav)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigator)
        bottomNavigationView.setOnItemSelectedListener(this as NavigationBarView.OnItemSelectedListener)
        bottomNavigationView.selectedItemId = R.id.homeId
    }

    var firstFragment: HomeFragment = HomeFragment()
    var secondFragment = OrderFragment()
    var thirdFragment = SettingsFragment()
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.homeId) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.f1Fragment, firstFragment)
                .commit()
            return true
        } else if (itemId == R.id.orderId) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.f1Fragment, secondFragment)
                .commit()
            return true
        } else if (itemId == R.id.settingsId) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.f1Fragment, thirdFragment)
                .commit()
            return true
        }
        return false
    }
}