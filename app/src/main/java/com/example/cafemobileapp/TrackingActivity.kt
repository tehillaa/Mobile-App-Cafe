package com.example.cafemobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TrackingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking)

        val orderId = intent.getStringExtra("orderId")
        val orderStatus = intent.getStringExtra("orderStatus")
        val orderItems = intent.getStringExtra("orderItems")
        val orderTotal = intent.getDoubleExtra("orderTotal", 0.0)

        findViewById<TextView>(R.id.trackingOrderId).text = "Order ID: $orderId"
        findViewById<TextView>(R.id.trackingOrderItems).text = "Items: $orderItems"
        findViewById<TextView>(R.id.trackingOrderTotal).text = "Total: £${String.format("%.2f", orderTotal)}"
        findViewById<TextView>(R.id.trackingOrderStatus).text = "Status: $orderStatus"

        // ✅ Back to My Orders button
        val backButton = findViewById<Button>(R.id.backToOrdersButton)
        backButton.setOnClickListener {
            startActivity(Intent(this, OrdersActivity::class.java))
            finish()
        }
    }
}

