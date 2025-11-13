package com.example.cafemobileapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Order(
    val id: String,
    val items: String,
    val total: Double,
    val status: String
)

class OrdersAdapter(
    private val orders: List<Order>,
    private val onClick: (Order) -> Unit
) : RecyclerView.Adapter<OrdersAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val orderId: TextView = view.findViewById(R.id.orderIdText)
        val orderItems: TextView = view.findViewById(R.id.orderItemsText)
        val orderTotal: TextView = view.findViewById(R.id.orderTotalText)
        val orderStatus: TextView = view.findViewById(R.id.orderStatusText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_item, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val order = orders[position]
        holder.orderId.text = "Order ID: ${order.id.takeLast(6)}"
        holder.orderItems.text = "Items: ${order.items}"
        holder.orderTotal.text = "Total: £${String.format("%.2f", order.total)}"
        holder.orderStatus.text = "Status: ${order.status}"

        holder.itemView.setOnClickListener { onClick(order) }
    }

    override fun getItemCount() = orders.size
}



