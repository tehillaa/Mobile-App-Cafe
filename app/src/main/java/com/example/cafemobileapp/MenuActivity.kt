package com.example.cafemobileapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent

class MenuActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val menuList = mutableListOf<MenuItem>()
    private lateinit var adapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MenuAdapter(menuList) { item, qty ->
            CartManager.addItem(item, qty)
            Toast.makeText(this, "${item.name} x$qty added to cart", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        // load menu when activity starts
        loadMenu()

        findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabCart)
            .setOnClickListener {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }

    }

    // 👇 Notice how loadMenu() is OUTSIDE onCreate()
    private fun loadMenu() {
        menuList.clear()
        menuList.addAll(
            listOf(
                MenuItem("Espresso", 2.20, "https://images.unsplash.com/photo-1509042239860-f550ce710b93"),
                MenuItem("Cappuccino", 3.00, "https://images.unsplash.com/photo-1511920170033-f8396924c348?w=800"),
                MenuItem("Latte", 3.20, "https://images.unsplash.com/photo-1510626176961-4b57d4fbad03"),
                MenuItem("Mocha", 3.50, "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=800"),
                MenuItem("Hot Chocolate", 3.00, "https://images.unsplash.com/photo-1498804103079-a6351b050096"),
                MenuItem("Croissant", 2.00, "https://images.unsplash.com/photo-1587248720329-01d8b62b8b6b?w=800"),
                MenuItem("Blueberry Muffin", 2.50, "https://images.unsplash.com/photo-1588196749597-9ff075ee6b5b?w=800"),
                MenuItem("Chicken Sandwich", 4.50, "https://images.unsplash.com/photo-1606755962773-d324d6a1e2f1?w=800"),
                MenuItem("Caesar Salad", 4.00, "https://images.unsplash.com/photo-1565958011705-44e211f7a6aa?w=800"),
                MenuItem("Iced Coffee", 3.20, "https://images.unsplash.com/photo-1511920170033-f8396924c348")
            )
        )
        adapter.notifyDataSetChanged()
    }
}




