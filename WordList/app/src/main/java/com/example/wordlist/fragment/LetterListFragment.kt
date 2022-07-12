package com.example.wordlist.fragment

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordlist.R
import com.example.wordlist.adapter.LetterAdapter
import com.example.wordlist.data.SettingsDataStore
import com.example.wordlist.databinding.FragmentLetterListBinding
import kotlinx.coroutines.launch

class LetterListFragment : Fragment() {
    private var _binding: FragmentLetterListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private var isLinearLayoutManager = true

    private lateinit var settingsDataStore: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLetterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        settingsDataStore = SettingsDataStore(requireContext())
        settingsDataStore.preferenceFlow.asLiveData().observe(viewLifecycleOwner) {
            isLinearLayoutManager = it
            chooseLayout()
            activity?.invalidateOptionsMenu()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val layoutButton = menu.findItem(R.id.action_switch_layout)
        setIcon(layoutButton)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_switch_layout) {
            isLinearLayoutManager = !isLinearLayoutManager
            setIcon(item)
            chooseLayout()
            lifecycleScope.launch {
                settingsDataStore.saveLayoutToPreferencesStore(
                    isLinearLayoutManager,
                    requireContext()
                )
            }
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun chooseLayout() {
        recyclerView.layoutManager = if (isLinearLayoutManager) LinearLayoutManager(context)
        else GridLayoutManager(context, 3)
        recyclerView.adapter = LetterAdapter()
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null) return
        menuItem.icon = ContextCompat.getDrawable(
            this.requireContext(), if (isLinearLayoutManager) R.drawable.ic_grid_layout
            else R.drawable.ic_linear_layout
        )
    }
}
