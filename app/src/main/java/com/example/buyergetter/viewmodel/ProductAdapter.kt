package com.example.buyergetter.viewmodel

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buyergetter.ProductDetailActivity
import com.example.buyergetter.R
import com.example.buyergetter.model.Products

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val productItems = mutableListOf<Products>()

    fun addItems(items: List<Products>) {
        productItems.clear()
        productItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_products, parent, false)
        return ProductViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productItems[position]
        holder.textViewName.text = product.name
        holder.textViewPrice.text = product.price
        holder.textViewDescription.text = product.description
        holder.ratingBar.rating = product.rating
        holder.imageView.setImageResource(product.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.context, ProductDetailActivity::class.java)
            intent.putExtra("name", product.name)
            intent.putExtra("price", product.price.replace("$", "").toDouble())
            intent.putExtra("description", product.description)
            intent.putExtra("rating", product.rating)
            intent.putExtra("image", product.image)
            holder.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return productItems.size
    }

    inner class ProductViewHolder(itemView: View, val context: Context) :
        RecyclerView.ViewHolder(itemView) {
        val imageView: AppCompatImageView = itemView.findViewById(R.id.image)
        val textViewName: AppCompatTextView = itemView.findViewById(R.id.tv_imgname)
        val textViewPrice: AppCompatTextView = itemView.findViewById(R.id.tv_price)
        val textViewDescription: AppCompatTextView = itemView.findViewById(R.id.tv_description)
        val ratingBar: RatingBar = itemView.findViewById(R.id.tv_rating)
    }
}
