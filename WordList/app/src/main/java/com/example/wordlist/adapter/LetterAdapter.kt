package com.example.wordlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.wordlist.R
import com.example.wordlist.fragment.LetterListFragment
import com.example.wordlist.fragment.LetterListFragmentDirections

/**
 * Adapter for the [RecyclerView] in [LetterListFragment].
 */
class LetterAdapter : RecyclerView.Adapter<LetterAdapter.LetterViewHolder>() {
    // Generates a [CharRange] from 'A' to 'Z' and converts it to a list
    private val list = ('A'..'Z').toList()

    /**
     * Provides a reference for the views needed to display items in your list.
     */
    class LetterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.button_item)
    }

    override fun getItemCount() = list.size

    /**
     * Creates new views with R.layout.word_list_item as its template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        // Setup custom accessibility delegate to set the text read
        layout.accessibilityDelegate = Accessibility
        return LetterViewHolder(layout)
    }

    /**
     * Replaces the content of an existing view with new data
     */
    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        holder.button.text = list[position].toString()
        holder.button.setOnClickListener {
            val action =
                LetterListFragmentDirections.actionLetterListFragmentToWordListFragment(
                    letter = holder.button.text.toString()
                )
            holder.itemView.findNavController().navigate(action)
        }
    }

    // Setup custom accessibility delegate to set the text read with
    // an accessibility service
    companion object Accessibility : View.AccessibilityDelegate() {
        //        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            // With `null` as the second argument to [AccessibilityAction], the
            // accessibility service announces "double tap to activate".
            // If a custom string is provided,
            // it announces "double tap to <custom string>".
            val customClick = AccessibilityNodeInfo.AccessibilityAction(
                AccessibilityNodeInfo.ACTION_CLICK, host?.context?.getString(R.string.look_up_words)
            )
            info?.addAction(customClick)
        }
    }
}
