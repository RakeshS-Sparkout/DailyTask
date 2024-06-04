package com.example.buyergetter.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import com.example.buyergetter.LoginActivity
import com.example.buyergetter.R
import com.example.buyergetter.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class SettingsFragment : Fragment() {

    private lateinit var editNameText: AppCompatEditText
    private lateinit var editEmailText: AppCompatEditText
    private lateinit var editPhoneText: AppCompatEditText
    private lateinit var editGenderText: AppCompatEditText
    private lateinit var radioGenderGroup: RadioGroup
    private lateinit var editDateText: AppCompatEditText
    private lateinit var editPasswordText: AppCompatEditText
    private lateinit var buttonDateSet: AppCompatButton
    private lateinit var buttonUpdate: AppCompatButton
    private lateinit var buttonLogout: AppCompatButton
    private lateinit var database: FirebaseDatabase
    private lateinit var usersRef: DatabaseReference
    private lateinit var sharedPrefs: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        editNameText = view.findViewById(R.id.et_nameSet)
        editEmailText = view.findViewById(R.id.et_emailSet)
        editPhoneText = view.findViewById(R.id.et_phoneSet)
        editGenderText = view.findViewById(R.id.et_genderSet)
        editDateText = view.findViewById(R.id.et_dobSet)
        editPasswordText = view.findViewById(R.id.et_passwordSet)
        radioGenderGroup = view.findViewById(R.id.rg_genderSet)
        buttonDateSet = view.findViewById(R.id.btn_reDate)
        buttonUpdate = view.findViewById(R.id.btn_update)
        buttonLogout = view.findViewById(R.id.btn_logout)

        database = FirebaseDatabase.getInstance()
        usersRef = database.getReference("users")
        sharedPrefs = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)

        val phoneNum = sharedPrefs.getString("phoneNum", "")
        if (!phoneNum.isNullOrEmpty()) {
            loadUserDetails(phoneNum)
        } else {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
        }

        editDateText.setOnClickListener {
            buttonDateSet.visibility = View.VISIBLE
        }

        editGenderText.setOnClickListener {
            radioGenderGroup.visibility = View.VISIBLE
        }

        buttonDateSet.setOnClickListener {
            showDatePicker()
        }

        radioGenderGroup.setOnCheckedChangeListener { _, checkedId ->
            val gender = when (checkedId) {
                R.id.rb_maleSet -> "Male"
                R.id.rb_femaleSet -> "Female"
                else -> "Others"
            }
            editGenderText.setText(gender)
            radioGenderGroup.visibility = View.GONE
        }

        buttonUpdate.setOnClickListener {
            val name = editNameText.text.toString()
            val email = editEmailText.text.toString()
            val phoneNum = editPhoneText.text.toString()
            val gender = editGenderText.text.toString()
            val date = editDateText.text.toString()
            val password = editPasswordText.text.toString()

            if (phoneNum.isNotEmpty()) {
                updateUserDetails(name, email, phoneNum, gender, date, password)
            }
        }

        buttonLogout.setOnClickListener {
            logoutUser()
        }

        return view
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                editDateText.setText(date)
                buttonDateSet.visibility = View.GONE
            }, year, month, day)

        datePickerDialog.show()
    }

    private fun loadUserDetails(phoneNum: String) {
        usersRef.child(phoneNum).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user != null) {
                    editNameText.setText(user.name)
                    editEmailText.setText(user.email)
                    editPhoneText.setText(user.phoneNum)
                    editGenderText.setText(user.gender)
                    editDateText.setText(user.date)
                    editPasswordText.setText(user.password)
                } else {
                    Toast.makeText(requireContext(), "User details not found", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Failed to load user details: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun updateUserDetails(
        name: String,
        email: String,
        phoneNum: String,
        gender: String,
        date: String,
        password: String
    ) {
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
                    requireContext(),
                    "User details updated successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { error ->
                Toast.makeText(
                    requireContext(),
                    "Failed to update user details: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun logoutUser() {
        val editor = sharedPrefs.edit()
        editor.clear()
        editor.apply()
        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}
