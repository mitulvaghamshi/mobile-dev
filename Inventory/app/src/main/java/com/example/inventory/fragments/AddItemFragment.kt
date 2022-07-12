package com.example.inventory.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventory.InventoryApplication
import com.example.inventory.data.Item
import com.example.inventory.databinding.FragmentAddItemBinding
import com.example.inventory.viewmodel.InventoryViewModel
import com.example.inventory.viewmodel.InventoryViewModelFactory
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment to add or update an item in the Inventory database.
 */
class AddItemFragment : Fragment() {
    private val navigationArgs: ItemDetailFragmentArgs by navArgs()

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    lateinit var item: Item

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId

        if (id > 0) {
            viewModel.retrieveItem(navigationArgs.itemId).observe(viewLifecycleOwner) {
                item = it
                bind(item)
            }
        } else {
            binding.saveAction.setOnClickListener { addNewItem() }
        }
    }

    private fun bind(item: Item) {
        val price = "%.2f".format(item.itemPrice)
        binding.apply {
            itemName.setText(item.itemName, TextView.BufferType.SPANNABLE)
            itemPrice.setText(price, TextView.BufferType.SPANNABLE)
            itemCount.setText(item.quantityInStock.toString(), TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateItem() }
        }
    }

    private fun isEntryValid() = viewModel.isEntryValid(
        binding.itemName.text.toString(),
        binding.itemPrice.text.toString(),
        binding.itemCount.text.toString()
    )

    private fun updateItem() {
        if (isEntryValid()) {
            viewModel.updateItem(
                Item(
                    navigationArgs.itemId,
                    binding.itemName.text.toString(),
                    binding.itemPrice.text.toString().toDouble(),
                    binding.itemCount.text.toString().toInt()
                )
            )
            findNavController().navigateUp()
        } else {
            Snackbar.make(requireView(), "Please provide the information!", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewItem(
                binding.itemName.text.toString(),
                binding.itemPrice.text.toString(),
                binding.itemCount.text.toString()
            )
            Snackbar.make(requireView(), "Item added successfully!", Snackbar.LENGTH_SHORT)
                .show()
            val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
            findNavController().navigate(action)
        } else {
            Snackbar.make(requireView(), "Please provide the information!", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity()
            .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            requireActivity().currentFocus?.windowToken, 0
        )
        _binding = null
    }
}
