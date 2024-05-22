package com.example.buyergetter.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buyergetter.R
import com.example.buyergetter.model.Products

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val productItems = mutableListOf<Products>()

    fun addItems(items: List<Products>) {
        productItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_products, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = productItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return productItems.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: AppCompatImageView = itemView.findViewById(R.id.image)
        private val textViewName: AppCompatTextView = itemView.findViewById(R.id.tv_imgname)
        private val textViewPrice: AppCompatTextView = itemView.findViewById(R.id.tv_price)
        private val textViewDescription: AppCompatTextView = itemView.findViewById(R.id.tv_description)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.tv_rating)

        fun bind(item: Products) {
            imageView.setImageResource(item.image)
            textViewName.text = item.name
            textViewPrice.text = item.price.toString()
            textViewDescription.text = item.description
            ratingBar.rating = item.rating
        }
    }
}
