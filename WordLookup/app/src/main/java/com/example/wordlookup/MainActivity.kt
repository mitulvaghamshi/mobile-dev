package com.example.wordlookup

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordlookup.adapter.LetterAdapter
import com.example.wordlookup.databinding.ActivityMainBinding

/**
 * Main Activity and entry point for the app.
 * Displays a RecyclerView of letters.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var isLinearLayoutManager = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.recyclerView
        chooseLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        val button = menu.findItem(R.id.action_switch_layout)
        setIcon(button)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                isLinearLayoutManager = !isLinearLayoutManager
                setIcon(item)
                chooseLayout()
                true
            }
            else -> true
        }
    }

    private fun chooseLayout() {
        recyclerView.layoutManager = if (isLinearLayoutManager) LinearLayoutManager(this)
        else GridLayoutManager(this, 3)
        // Sets the LinearLayoutManager of the recyclerview
        recyclerView.adapter = LetterAdapter()
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null) return
        // Set the drawable for the menu icon based on which LayoutManager is currently in use
        // An if-clause can be used on the right side of an assignment if all paths return a value.
        // The following code is equivalent to
        // if (isLinearLayoutManager)
        //      menu.icon = ContextCompat.getDrawable(this, R.drawable.ic_grid_layout)
        // else menu.icon = ContextCompat.getDrawable(this, R.drawable.ic_linear_layout)
        menuItem.icon = ContextCompat.getDrawable(
            this, if (isLinearLayoutManager) R.drawable.ic_grid_layout
            else R.drawable.ic_linear_layout
        )
    }
}
