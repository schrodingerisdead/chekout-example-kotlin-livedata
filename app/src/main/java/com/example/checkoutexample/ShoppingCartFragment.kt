package com.example.checkoutexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.checkoutexample.databinding.FragmentShoppingCartBinding

class ShoppingCartFragment : Fragment() {

    private var _binding: FragmentShoppingCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ShoppingCartViewModel
    private lateinit var viewModelFactory: ShoppingCartViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using view binding
        _binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
        val view = binding.root

        // Extract order total from arguments using Safe Args
        val orderTotal = ShoppingCartFragmentArgs.fromBundle(requireArguments()).orderTotal

        // Create ViewModelFactory with the order total and initialize ViewModel
        viewModelFactory = ShoppingCartViewModelFactory(orderTotal)
        viewModel = ViewModelProvider(this, viewModelFactory)[ShoppingCartViewModel::class.java]

        binding.apply {
            // Display the order total in the shopping cart
            orderTotalAmount.text = viewModel.generateCartMessage()

            // Handle edit order button click to navigate back to ProductDetailFragment
            editOrderButton.setOnClickListener {
                val action = ShoppingCartFragmentDirections.actionShoppingCartFragmentToProductDetailFragment()
                view.findNavController().navigate(action)
            }
        }
        return view
    }

}

