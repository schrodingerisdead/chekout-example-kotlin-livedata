package com.example.checkoutexample

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.checkoutexample.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductDetailViewModel
    private var stockLeft = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using view binding
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[ProductDetailViewModel::class.java]

        binding.apply {
            // Observe order total LiveData
            viewModel.orderTotal.observe(viewLifecycleOwner) { orderTotal ->
                // Update checkout button text with order total
                with(checkoutButton) {
                    text = getString(R.string.checkout, orderTotal)
                    // Navigate to ShoppingCartFragment when checkout button is clicked
                    setOnClickListener {
                        val action =
                            ProductDetailFragmentDirections.actionProductDetailFragmentToShoppingCartFragment(
                                orderTotal
                            )
                        view.findNavController().navigate(action)
                    }
                }
            }

            // Observe remaining stock LiveData
            viewModel.remainingStock.observe(viewLifecycleOwner) { remainingStock ->
                // Update stock count message
                stockCountMessage.text = getString(R.string.stock_msg, remainingStock)
                stockLeft = remainingStock

                // Disable addToCartButton and display out of stock message if stock is 0
                if (remainingStock == 0) {
                    addToCartButton.isEnabled = false
                    stockCountMessage.apply {
                        text = getString(R.string.out_of_stock)
                        setTextColor(Color.RED)
                    }
                }
            }

            // Observe insufficient stock LiveData
            viewModel.insufficientStock.observe(viewLifecycleOwner) { insufficientStock ->
                // Display toast message if there's insufficient stock
                if (insufficientStock) {
                    Toast.makeText(
                        context,
                        getString(R.string.low_stock_error, stockLeft),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            // Handle addToCartButton click
            addToCartButton.setOnClickListener {
                // Get order quantity from input
                val orderQuantity = quantityNumber.text.toString().toInt()
                // Call ViewModel function to handle add to cart action
                viewModel.onAddButtonClicked(orderQuantity)

                // Hide keyboard after clicking addToCartButton
                hideKeyboard(view)
            }
        }

        return view
    }

    // Function to hide the keyboard
    private fun hideKeyboard(view: ScrollView) {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
