package com.example.buyergetter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // Apply window insets to handle system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val nextActivity = if (isUserLoggedIn()) {
                BottomNavigationActivity::class.java
            } else {
                LoginActivity::class.java
            }
            startActivity(Intent(this, nextActivity))
            finish()
        }, splashTimeOut)
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPrefs = getSharedPreferences("userData", Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean("isLoggedIn", false)
    }
}
