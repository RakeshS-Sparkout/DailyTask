package com.example.buyergetter.viewmodel

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.buyergetter.ProductDetailActivity
import com.example.buyergetter.R
import com.example.buyergetter.model.Products

class ProductAdapter :
    ListAdapter<Products, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_products, parent, false)
        return ProductViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    inner class ProductViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val imageView: AppCompatImageView = itemView.findViewById(R.id.image)
        private val textViewName: AppCompatTextView = itemView.findViewById(R.id.tv_imgname)
        private val textViewPrice: AppCompatTextView = itemView.findViewById(R.id.tv_price)
        private val textViewDescription: AppCompatTextView =
            itemView.findViewById(R.id.tv_description)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.tv_rating)

        fun bind(product: Products) {
            textViewName.text = product.name
            textViewPrice.text = product.price.toString()
            textViewDescription.text = product.description
            ratingBar.rating = product.rating
            imageView.setImageResource(product.image)

            itemView.setOnClickListener {
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("name", product.name)
                intent.putExtra("price", product.price)
                intent.putExtra("description", product.description)
                intent.putExtra("rating", product.rating)
                intent.putExtra("image", product.image)
                context.startActivity(intent)
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }
    }
}
