package com.example.cafemobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class OrdersActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var adapter: OrdersAdapter
    private val ordersList = mutableListOf<Order>()
    private var listenerRegistration: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        recyclerView = findViewById(R.id.ordersRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ✅ Adapter that opens tracking screen when an order is tapped
        adapter = OrdersAdapter(ordersList) { order ->
            val intent = Intent(this, TrackingActivity::class.java)
            intent.putExtra("orderId", order.id)
            intent.putExtra("orderStatus", order.status)
            intent.putExtra("orderItems", order.items)
            intent.putExtra("orderTotal", order.total)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // ✅ Back to Menu button
        val backButton = findViewById<Button>(R.id.backToMenuButton)
        backButton.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

        listenToOrders()
    }

    private fun listenToOrders() {
        val userEmail = auth.currentUser?.email ?: "guest"

        listenerRegistration = db.collection("orders")
            .whereEqualTo("user", userEmail)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Toast.makeText(this, "Error loading orders: ${e.message}", Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                ordersList.clear()
                if (snapshot == null || snapshot.isEmpty) {
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "No orders found.", Toast.LENGTH_SHORT).show()
                } else {
                    for (doc in snapshot.documents) {
                        val id = doc.id
                        val total = doc.getDouble("total") ?: 0.0
                        val status = doc.getString("status") ?: "Unknown"
                        val itemsList = doc.get("items") as? List<Map<String, Any>> ?: emptyList()
                        val itemNames = itemsList.joinToString { it["name"].toString() }

                        ordersList.add(Order(id, itemNames, total, status))
                    }
                    adapter.notifyDataSetChanged()
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        listenerRegistration?.remove()
    }
}



