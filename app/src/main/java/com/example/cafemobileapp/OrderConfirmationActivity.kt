package com.example.cafemobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmation)

        val messageText: TextView = findViewById(R.id.thankYouMessage)
        val btnTrackOrders: Button = findViewById(R.id.btnTrackOrders)
        val btnGoToMenu: Button = findViewById(R.id.btnGoToMenu)
        val btnLeaveFeedback: Button = findViewById(R.id.btnLeaveFeedback)

        messageText.text = "Thanks for your order! 🎉\nYour order is being processed."

        btnTrackOrders.setOnClickListener {
            val intent = Intent(this, OrdersActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnGoToMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLeaveFeedback.setOnClickListener {
            val intent = Intent(this, FeedbackActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
