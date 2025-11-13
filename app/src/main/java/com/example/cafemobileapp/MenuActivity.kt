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
                MenuItem("Espresso", 2.20, "https://comohacercafe.com/wp-content/uploads/2020/03/espresso.jpeg"),
                MenuItem("Cappuccino", 3.00, "https://images.unsplash.com/photo-1511920170033-f8396924c348?w=800"),
                MenuItem("Latte", 3.20, "https://brookrest.com/wp-content/uploads/2020/05/AdobeStock_315919556-scaled.jpeg"),
                MenuItem("Mocha", 3.50, "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=800"),
                MenuItem("Hot Chocolate", 3.00, "https://images.unsplash.com/photo-1498804103079-a6351b050096"),
                MenuItem("Croissant", 2.00, "https://www.theflavorbender.com/wp-content/uploads/2020/05/French-Croissants-SM-2363.jpg"),
                MenuItem("Blueberry Muffin", 2.50, "https://cdn3.tmbi.com/secure/RMS/attachments/37/1200x1200/Blueberry-Muffins_exps2251_W101973175A05_18_1bC_RMS.jpg"),
                MenuItem("Chicken Sandwich", 4.50, "https://static.wixstatic.com/media/d09a8429a4594780aa9f7b53a3875b51.jpg/v1/crop/x_67,y_0,w_5615,h_3798/fill/w_927,h_627,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/Chicken%20Sandwich.jpg"),
                MenuItem("Caesar Salad", 4.00, "https://www.thespruceeats.com/thmb/Z6IWF7c9zywuU9maSIimGLbHoI4=/3000x2000/filters:fill(auto,1)/classic-caesar-salad-recipe-996054-Hero_01-33c94cc8b8e841ee8f2a815816a0af95.jpg"),
                MenuItem("Iced Coffee", 3.20, "https://theheirloompantry.co/wp-content/uploads/2022/10/how-to-make-iced-coffee-at-home-the-heirloom-pantry-2.jpg")
            )
        )
        adapter.notifyDataSetChanged()
    }
}




