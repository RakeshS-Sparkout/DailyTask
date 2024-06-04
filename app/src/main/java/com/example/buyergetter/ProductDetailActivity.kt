package com.example.buyergetter

import android.os.Bundle
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.lifecycleScope
import com.example.buyergetter.model.AppDatabase
import com.example.buyergetter.model.CartItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var imageView: AppCompatImageView
    private lateinit var nameTextView: AppCompatTextView
    private lateinit var priceTextView: AppCompatTextView
    private lateinit var descriptionTextView: AppCompatTextView
    private lateinit var ratingBar: RatingBar
    private lateinit var quantityTextView: AppCompatTextView
    private lateinit var totalAmountTextView: AppCompatTextView
    private lateinit var increaseButton: AppCompatButton
    private lateinit var decreaseButton: AppCompatButton
    private lateinit var addCartButton: AppCompatButton

    private var quantity: Int = 1
    private var price: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        imageView = findViewById(R.id.detail_image)
        nameTextView = findViewById(R.id.tv_nameDetail)
        priceTextView = findViewById(R.id.tv_priceDetail)
        descriptionTextView = findViewById(R.id.tv_detailDescription)
        ratingBar = findViewById(R.id.detail_ratingbar)
        quantityTextView = findViewById(R.id.tv_quantity)
        totalAmountTextView = findViewById(R.id.tv_total_amount)
        increaseButton = findViewById(R.id.btn_increase)
        decreaseButton = findViewById(R.id.btn_decrease)
        addCartButton = findViewById(R.id.btn_to_cart)

        intent?.let {
            val name = it.getStringExtra("name") ?: "Unknown"
            price = it.getDoubleExtra("price", 0.0)
            val description = it.getStringExtra("description") ?: "No description"
            val rating = it.getFloatExtra("rating", 0f)
            val image = it.getIntExtra("image", 0)

            nameTextView.text = name
            priceTextView.text = String.format("$%.2f", price)
            descriptionTextView.text = description
            ratingBar.rating = rating
            imageView.setImageResource(image)

            updateQuantity(quantity)

            increaseButton.setOnClickListener {
                increaseQuantity()
            }

            decreaseButton.setOnClickListener {
                decreaseQuantity()
            }

            addCartButton.setOnClickListener {
                addToCart(name, price, quantity, image)
            }
        }
    }

    private fun increaseQuantity() {
        quantity++
        updateQuantity(quantity)
    }

    private fun decreaseQuantity() {
        if (quantity > 1) {
            quantity--
            updateQuantity(quantity)
        } else {
            Toast.makeText(this, "Quantity cannot be less than 1", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateQuantity(quantity: Int) {
        quantityTextView.text = quantity.toString()
        updateTotalAmount(quantity, price)
    }

    private fun updateTotalAmount(quantity: Int, price: Double) {
        val totalAmount = quantity * price
        totalAmountTextView.text = getString(R.string.total_amount_format, totalAmount)
    }

    private fun addToCart(name: String, price: Double, quantity: Int, image: Int) {
        val amount = quantity * price
        val cartItem = CartItem(
            name = name,
            price = price,
            quantity = quantity,
            image = image,
            amount = amount
        )

        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(applicationContext)
            db.cartDao().clearCart()
            db.cartDao().insert(cartItem)

            withContext(Dispatchers.Main) {
                Toast.makeText(this@ProductDetailActivity, "Added to cart", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }
}
