package com.example.checkoutexample

import androidx.lifecycle.ViewModel

class ShoppingCartViewModel(val orderTotal: Int): ViewModel() {
    // Function to generate a message based on the order total
    fun generateCartMessage(): String {
        // Check if the order total is less than 1
        return if (orderTotal < 1) {
            // Return a message indicating an empty cart if the order total is less than 1
            "Your cart is empty"
        } else {
            // Return a formatted message with the order total if the order total is greater than or equal to 1
            """
                Thanks for your order!
                
                Your total is: $$orderTotal
            """.trimIndent()
        }
    }
}
