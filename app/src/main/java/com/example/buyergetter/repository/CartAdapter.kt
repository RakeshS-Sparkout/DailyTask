package com.example.buyergetter.repository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buyergetter.R
import com.example.buyergetter.model.CartItem

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onPlaceOrderClick: (CartItem) -> Unit,
    private val onRemoveClick: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    fun setItems(items: List<CartItem>) {
        cartItems = items
        notifyDataSetChanged()
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewCartImage: AppCompatImageView = itemView.findViewById(R.id.cart_image)
        val viewCartName: AppCompatTextView = itemView.findViewById(R.id.tv_cart_name)
        val viewCartPrice: AppCompatTextView = itemView.findViewById(R.id.tv_cart_price)
        val viewCartQuantity: AppCompatTextView = itemView.findViewById(R.id.tv_cart_quantity)
        val viewCartAmount: AppCompatTextView = itemView.findViewById(R.id.tv_cart_total_amount)
        val placeOrderButton: AppCompatButton = itemView.findViewById(R.id.btn_place_order)
        val removeCartButton: AppCompatButton = itemView.findViewById(R.id.btn_delete_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]

        holder.viewCartImage.setImageResource(cartItem.image)
        holder.viewCartName.text = cartItem.name
        holder.viewCartPrice.text = cartItem.price.toString()
        holder.viewCartQuantity.text = cartItem.quantity.toString()
        holder.viewCartAmount.text = cartItem.amount.toString()

        holder.placeOrderButton.setOnClickListener {
            onPlaceOrderClick(cartItem)
        }
        holder.removeCartButton.setOnClickListener {
            onRemoveClick(cartItem)
        }
    }
}
