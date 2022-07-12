package com.example.diceroller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.diceroller.databinding.ActivityMainBinding

/**
 * This activity allows user to roll a dice
 * and view the result in the screen.
 */
class MainActivity : AppCompatActivity() {
    // Create an object of Dice with 6 sides.
    private val dice = Dice(6)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)
            buttonRoll.setOnClickListener {
                imageViewDice.setImageResource(dice.roll())
            }
        }
    }
}

/**
 * Logic to roll a dice
 */
class Dice(private val sides: Int) {
    /**
     * Generate and return a number between 1 and 6.
     */
    fun roll() = when ((1..sides).random()) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
}