package com.example.buyergetter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextPhoneNum: AppCompatEditText
    private lateinit var editTextPassword: AppCompatEditText
    private lateinit var buttonLogin: AppCompatButton
    private lateinit var textSignUp: AppCompatTextView
    private lateinit var database: FirebaseDatabase
    private lateinit var usersRef: DatabaseReference
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextPhoneNum = findViewById(R.id.et_phone)
        editTextPassword = findViewById(R.id.et_password)
        buttonLogin = findViewById(R.id.btn_login)
        textSignUp = findViewById(R.id.tv_signup)

        database = FirebaseDatabase.getInstance()
        usersRef = database.getReference("users")

        sharedPrefs = getSharedPreferences("userData", Context.MODE_PRIVATE)

        buttonLogin.setOnClickListener {
            val phoneNum = editTextPhoneNum.text.toString()
            val password = editTextPassword.text.toString()
            loginUser(phoneNum, password)
        }

        textSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        editTextPhoneNum.setText(getSavedPhoneNumber())
    }

    private fun getSavedPhoneNumber(): String? {
        return sharedPrefs.getString("phoneNum", "")
    }

    private fun loginUser(phoneNum: String, password: String) {
        if (phoneNum.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        usersRef.child(phoneNum).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val user = dataSnapshot.getValue(User::class.java)
                    if (user != null && user.password == password) {
                        saveUserLocally(phoneNum)
                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT)
                            .show()
                        val intent =
                            Intent(this@LoginActivity, ShopActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Incorrect Password", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "You are a new user, please register first",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent2 = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent2)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    this@LoginActivity,
                    "Login Failed: ${databaseError.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun saveUserLocally(phoneNum: String) {
        val editor = sharedPrefs.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.putString("phoneNum", phoneNum)
        editor.apply()
    }
}
