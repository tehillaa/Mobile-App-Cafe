package com.example.cafemobileapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var totalText: TextView
    private lateinit var checkoutButton: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.cartRecyclerView)
        totalText = findViewById(R.id.totalText)
        checkoutButton = findViewById(R.id.checkoutButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        refreshCart()

        checkoutButton.setOnClickListener {
            placeOrder()
        }
    }

    private fun refreshCart() {
        val items = CartManager.getItems()
        val adapter = CartAdapter(items) { cartItem ->
            CartManager.removeItem(cartItem)
            refreshCart()
        }
        recyclerView.adapter = adapter
        totalText.text = "Total: £${String.format("%.2f", CartManager.getTotal())}"
    }

    private fun placeOrder() {
        val items = CartManager.getItems()
        if (items.isEmpty()) {
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show()
            return
        }

        val order = hashMapOf(
            "user" to (com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.email ?: "guest"),
            "total" to CartManager.getTotal(),
            "items" to items.map { mapOf("name" to it.name, "price" to it.price, "qty" to it.quantity) },
            "status" to "Placed",
            "timestamp" to com.google.firebase.Timestamp.now()
        )

        db.collection("orders")
            .add(order)
            .addOnSuccessListener {
                Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT).show()
                CartManager.clearCart()
                refreshCart()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Order failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
