package com.example.cafemobileapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(private val items: List<CartItem>, private val onRemove: (CartItem) -> Unit) :
    RecyclerView.Adapter<CartAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.cartItemName)
        val qty: TextView = view.findViewById(R.id.cartItemQty)
        val price: TextView = view.findViewById(R.id.cartItemPrice)
        val btnRemove: Button = view.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val ci = items[position]
        holder.name.text = ci.name
        holder.price.text = "£${String.format("%.2f", ci.price * ci.quantity)}"
        holder.btnRemove.setOnClickListener { onRemove(ci) }
    }

    override fun getItemCount() = items.size
}
