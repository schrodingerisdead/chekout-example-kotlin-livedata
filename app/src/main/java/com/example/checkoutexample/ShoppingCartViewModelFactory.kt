package com.example.checkoutexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShoppingCartViewModelFactory(private val orderTotal: Int): ViewModelProvider.Factory {
    // Override the create method to create an instance of the ViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Create an instance of the ViewModel using reflection and pass the order total
        return modelClass.getConstructor(Int::class.java).newInstance(orderTotal)
    }
}
