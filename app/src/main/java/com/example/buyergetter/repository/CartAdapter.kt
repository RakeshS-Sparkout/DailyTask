package com.example.buyergetter.repository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.buyergetter.R
import com.example.buyergetter.model.CartItem

class CartAdapter(
    private val deleteCartItem: (Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>()  {

    private val cartItems = mutableListOf<CartItem>()

    fun addItems(items: LiveData<List<CartItem>>) {
        cartItems.clear()
        cartItems.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart,parent,false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.textNameView.text = cartItem.name
        holder.textPriceView.text = cartItem.price.toString()
        holder.textQuantityView.text = cartItem.quantity.toString()
        holder.textAmountView.text = cartItem.amount.toString()
        holder.viewCartImage.setImageResource(cartItem.image)

        holder.deleteButton.setOnClickListener {
            deleteCartItem(cartItem.id)
        }
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val viewCartImage: AppCompatImageView = itemView.findViewById(R.id.cart_image)
        val textNameView: AppCompatTextView = itemView.findViewById(R.id.tv_cart_name)
        val textPriceView: AppCompatTextView = itemView.findViewById(R.id.tv_cart_price)
        val textQuantityView: AppCompatTextView = itemView.findViewById(R.id.tv_cart_quantity)
        val textAmountView: AppCompatTextView = itemView.findViewById(R.id.tv_cart_total_amount)
        val deleteButton: AppCompatButton = itemView.findViewById(R.id.btn_delete_order)
    }
}