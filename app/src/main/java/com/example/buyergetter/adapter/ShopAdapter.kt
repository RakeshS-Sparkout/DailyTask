package com.example.buyergetter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buyergetter.R
import com.example.buyergetter.model.Shop

class ShopAdapter(private val onShopClick: (Shop) -> Unit) :
    RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    private val shopList = mutableListOf<Shop>()

    fun addItems(items: List<Shop>) {
        shopList.clear()
        shopList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shop, parent, false)
        return ShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val shop = shopList[position]
        holder.bind(shop, onShopClick)
    }

    override fun getItemCount(): Int = shopList.size

    inner class ShopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textShopName: AppCompatTextView = itemView.findViewById(R.id.tv_shop_name)
        private val textShopRating: RatingBar = itemView.findViewById(R.id.shop_rating)
        private val textShopAddress: AppCompatTextView =
            itemView.findViewById(R.id.tv_shop_location)
        private val textShopImage: AppCompatImageView = itemView.findViewById(R.id.shop_image)
        private val textShopDescription: AppCompatTextView =
            itemView.findViewById(R.id.tv_shop_description)

        fun bind(shop: Shop, onShopClick: (Shop) -> Unit) {
            textShopName.text = shop.shopName
            textShopRating.rating = shop.rating
            textShopAddress.text = shop.address
            textShopImage.setImageResource(shop.photo)
            textShopDescription.text = shop.description

            itemView.setOnClickListener { onShopClick(shop) }
        }
    }
}