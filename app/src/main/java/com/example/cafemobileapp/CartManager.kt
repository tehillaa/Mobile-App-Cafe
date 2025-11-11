package com.example.cafemobileapp

object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    fun addItem(menuItem: MenuItem, quantity: Int) {
        val itemName = menuItem.name ?: "Unknown Item"
        val itemPrice = menuItem.price ?: 0.0

        val existingItem = cartItems.find { it.name == itemName }
        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            cartItems.add(CartItem(itemName, itemPrice, quantity))
        }
    }

    fun getItems(): List<CartItem> = cartItems

    fun removeItem(cartItem: CartItem) {
        cartItems.remove(cartItem)
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun getTotal(): Double {
        return cartItems.sumOf { it.price * it.quantity }
    }
}





