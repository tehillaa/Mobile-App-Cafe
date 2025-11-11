package com.example.cafemobileapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MenuAdapter(
    private val items: List<MenuItem>,
    private val onAddToCart: (MenuItem, Int) -> Unit
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val itemName: TextView = view.findViewById(R.id.itemName)
        val itemPrice: TextView = view.findViewById(R.id.itemPrice)
        val btnMinus: Button = view.findViewById(R.id.btnMinus)
        val btnPlus: Button = view.findViewById(R.id.btnPlus)
        val qtyText: TextView = view.findViewById(R.id.qtyText)
        val addToCartButton: Button = view.findViewById(R.id.addToCartButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        holder.itemName.text = model.name
        holder.itemPrice.text = "£${String.format("%.2f", model.price ?: 0.0)}"

        // set image via Glide (handles URLs)
        if (!model.imageUrl.isNullOrBlank()) {
            Glide.with(holder.itemView.context)
                .load(model.imageUrl)
                .centerCrop()
                .into(holder.itemImage)
        } else {
            holder.itemImage.setImageResource(R.drawable.ic_launcher_foreground)
        }

        // quantity handling
        holder.qtyText.text = "1"
        holder.btnPlus.setOnClickListener {
            val current = holder.qtyText.text.toString().toIntOrNull() ?: 1
            holder.qtyText.text = (current + 1).toString()
        }
        holder.btnMinus.setOnClickListener {
            val current = holder.qtyText.text.toString().toIntOrNull() ?: 1
            if (current > 1) holder.qtyText.text = (current - 1).toString()
        }

        // add to cart
        holder.addToCartButton.setOnClickListener {
            val qty = holder.qtyText.text.toString().toIntOrNull() ?: 1
            onAddToCart(model, qty)
            // reset qty to 1 after adding (optional)
            holder.qtyText.text = "1"
        }

    }

    override fun getItemCount(): Int = items.size
}


