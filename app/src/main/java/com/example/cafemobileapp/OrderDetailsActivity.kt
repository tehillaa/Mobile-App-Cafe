package com.example.cafemobileapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class OrderDetailsActivity : AppCompatActivity() {

    private lateinit var orderIdText: TextView
    private lateinit var orderStatusText: TextView
    private lateinit var orderItemsText: TextView
    private lateinit var orderTotalText: TextView
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        orderIdText = findViewById(R.id.orderIdText)
        orderStatusText = findViewById(R.id.orderStatusText)
        orderItemsText = findViewById(R.id.orderItemsText)
        orderTotalText = findViewById(R.id.orderTotalText)

        val orderId = intent.getStringExtra("ORDER_ID")
        if (orderId != null) {
            fetchOrderDetails(orderId)
        } else {
            orderIdText.text = "Order not found"
        }
    }

    private fun fetchOrderDetails(orderId: String) {
        db.collection("orders").document(orderId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val status = document.getString("status") ?: "Unknown"
                    val items = document.get("items") as? List<*> ?: emptyList<String>()
                    val total = document.getDouble("total") ?: 0.0

                    orderIdText.text = "Order ID: $orderId"
                    orderStatusText.text = "Status: $status"
                    orderItemsText.text = "Items:\n" + items.joinToString("\n• ", prefix = "• ")
                    orderTotalText.text = "Total: £%.2f".format(total)
                } else {
                    orderIdText.text = "Order not found"
                }
            }
            .addOnFailureListener {
                orderIdText.text = "Failed to load order details"
            }
    }
}
