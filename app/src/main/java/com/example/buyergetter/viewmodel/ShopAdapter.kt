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
import com.example.buyergetter.BottomNavigationActivity
import com.example.buyergetter.R
import com.example.buyergetter.model.Shop

class ShopAdapter : RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    private val shopList = mutableListOf<Shop>()

    fun addItems(items: List<Shop>) {
        shopList.clear()
        shopList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopAdapter.ShopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shop, parent, false)
        return ShopViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ShopAdapter.ShopViewHolder, position: Int) {
        val shop = shopList[position]
        holder.textShopName.text = shop.shopName
        holder.textShopRating.rating = shop.rating
        holder.textShopAddress.text = shop.address
        holder.textShopImage.setImageResource(shop.photo)
        holder.textShopDescription.text = shop.description

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.context, BottomNavigationActivity::class.java)
            holder.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    inner class ShopViewHolder(itemView: View, val context: Context) :
        RecyclerView.ViewHolder(itemView) {
        val textShopName: AppCompatTextView = itemView.findViewById(R.id.tv_shop_name)
        val textShopRating: RatingBar = itemView.findViewById(R.id.shop_rating)
        val textShopAddress: AppCompatTextView = itemView.findViewById(R.id.tv_shop_location)
        val textShopImage: AppCompatImageView = itemView.findViewById(R.id.shop_image)
        val textShopDescription: AppCompatTextView = itemView.findViewById(R.id.tv_shop_description)
    }

}