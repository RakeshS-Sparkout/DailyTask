package com.example.buyergetter.repository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buyergetter.R
import com.example.buyergetter.model.Order

class OrderAdapter(
    private val orders: List<Order>
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private val orderItems = mutableListOf<Order>()

    fun setItems(items: List<Order>) {
        orderItems.clear()
        orderItems.addAll(items)
        notifyDataSetChanged()
    }

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewOrderImage: AppCompatImageView = itemView.findViewById(R.id.order_image)
        val viewOrderName: AppCompatTextView = itemView.findViewById(R.id.tv_product_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderItems.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderItems[position]
        val firstItem = order.items.firstOrNull()
        firstItem?.let {
            holder.viewOrderImage.setImageResource(it.image)
            holder.viewOrderName.text = it.name
        }
    }
}
