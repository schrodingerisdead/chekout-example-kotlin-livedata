package com.example.checkoutexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductDetailViewModel : ViewModel() {
    // Define the price of each product
    private val price = 5

    // LiveData for remaining stock
    private val _remainingStock = MutableLiveData(5)
    val remainingStock: LiveData<Int> get() = _remainingStock

    // LiveData for total order amount
    private val _orderTotal = MutableLiveData(0)
    val orderTotal: LiveData<Int> get() = _orderTotal

    // LiveData for tracking insufficient stock
    private val _insufficientStock = MutableLiveData(false)
    val insufficientStock: LiveData<Boolean> get() = _insufficientStock

    // Function to handle add to cart button click
    fun onAddButtonClicked(quantity: Int) {
        _remainingStock.value?.let {
            // Check if there is sufficient stock
            _insufficientStock.value = if (quantity <= it) {
                // If there's sufficient stock, update stock and order total
                updateStock(quantity)
                updateOrderTotal(quantity)
                false
            } else {
                // If there's insufficient stock, set insufficientStock LiveData to true
                true
            }
        }
    }

    // Function to update the order total based on the quantity of products added
    private fun updateOrderTotal(quantity: Int) {
        _orderTotal.value = _orderTotal.value?.plus(quantity * price)
    }

    // Function to update the remaining stock after adding products to the cart
    private fun updateStock(quantity: Int) {
        _remainingStock.value = _remainingStock.value?.minus(quantity)
    }
}
