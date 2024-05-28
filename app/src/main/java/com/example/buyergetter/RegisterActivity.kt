package com.example.buyergetter

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextName: AppCompatEditText
    private lateinit var editTextMail: AppCompatEditText
    private lateinit var editTextPhone: AppCompatEditText
    private lateinit var editTextPass: AppCompatEditText
    private lateinit var editTextConfirm: AppCompatEditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var editTextDate: AppCompatEditText
    private lateinit var buttonDOB: AppCompatButton
    private lateinit var buttonRegister: AppCompatButton
    private lateinit var database: FirebaseDatabase
    private lateinit var usersRef: DatabaseReference
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextName = findViewById(R.id.et_name)
        editTextMail = findViewById(R.id.et_email)
        radioGroupGender = findViewById(R.id.rg_gender)
        buttonDOB = findViewById(R.id.btn_date_picker)
        editTextPhone = findViewById(R.id.et_phonenum)
        editTextPass = findViewById(R.id.et_pass)
        editTextConfirm = findViewById(R.id.et_confirm)
        editTextDate = findViewById(R.id.et_date)
        buttonRegister = findViewById(R.id.btn_register)

        database = FirebaseDatabase.getInstance()
        usersRef = database.getReference("users")

        buttonDOB.setOnClickListener {
            showDatePickerDialog()
        }

        sharedPrefs = getSharedPreferences("userData", Context.MODE_PRIVATE)

        buttonRegister.setOnClickListener {
            val name = editTextName.text.toString()
            val email = editTextMail.text.toString()
            val gender = getSelectedGender()
            val date = editTextDate.text.toString()
            val phoneNum = editTextPhone.text.toString()
            val password = editTextPass.text.toString()
            val rePassword = editTextConfirm.text.toString()

            registerUser(name, email, phoneNum, gender, date, password, rePassword)
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                editTextDate.setText(date)
            }, year, month, day)

        datePickerDialog.show()
    }

    private fun getSelectedGender(): String {
        return when (radioGroupGender.checkedRadioButtonId) {
            R.id.rb_male -> "Male"
            R.id.rb_female -> "Female"
            else -> "Others"
        }
    }

    private fun registerUser(
        name: String,
        email: String,
        phoneNum: String,
        gender: String,
        date: String,
        password: String,
        rePassword: String
    ) {
        if (name.isEmpty() || email.isEmpty() || phoneNum.isEmpty() || gender.isEmpty() || date.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != rePassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        usersRef.child(phoneNum).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "User is already registered",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val userDetails = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "phoneNum" to phoneNum,
                        "gender" to gender,
                        "date" to date,
                        "password" to password
                    )
                    usersRef.child(phoneNum).setValue(userDetails)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registered Successfully",
                                Toast.LENGTH_LONG
                            ).show()
                            saveRegisterData(phoneNum)
                            val intent =
                                Intent(this@RegisterActivity, ShopActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener { error ->
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registration Failed: ${error.message}",
                                Toast.LENGTH_LONG
                            ).show()
                            Log.e("RegisterActivity", "Registration failed", error)
                        }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Error: ${databaseError.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun saveRegisterData(phoneNum: String) {
        val editor = sharedPrefs.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.putString("phoneNum", phoneNum)
        editor.apply()
    }

    private fun enableEdgeToEdge() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }
}
