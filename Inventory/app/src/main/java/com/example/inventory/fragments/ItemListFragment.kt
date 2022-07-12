package com.example.inventory.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventory.R
import com.example.inventory.databinding.FragmentItemListBinding
import com.example.inventory.InventoryApplication
import com.example.inventory.adapter.ItemListAdapter
import com.example.inventory.viewmodel.InventoryViewModel
import com.example.inventory.viewmodel.InventoryViewModelFactory

/**
 * Main fragment displaying details for all items in the database.
 */
class ItemListFragment : Fragment() {
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ItemListAdapter {
            val action = ItemListFragmentDirections
                .actionItemListFragmentToItemDetailFragment(it.id)
            findNavController().navigate(action)
        }
        binding.inventoryRecyclerView.adapter = adapter
        binding.inventoryRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.floatingActionButton.setOnClickListener {
            val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment(
                getString(R.string.add_fragment_title)
            )
            findNavController().navigate(action)
        }
        viewModel.allItems.observe(viewLifecycleOwner) { it.let { adapter.submitList(it) } }
    }
}
