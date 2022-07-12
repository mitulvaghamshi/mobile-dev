package com.example.unscramble

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private var wordList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    private val _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score

    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount: MutableLiveData<Int> get() = _currentWordCount

    private val _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord: LiveData<Spannable> = Transformations.map(_currentScrambledWord) {
        if (it == null) {
            SpannableString("")
        } else {
            val scrambleWord = it.toString()
            val spannable: Spannable = SpannableString(scrambleWord)
            spannable.setSpan(
                TtsSpan.VerbatimBuilder(scrambleWord).build(),
                0, scrambleWord.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            spannable
        }
    }

    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        while (String(tempWord).equals(currentWord, false)) tempWord.shuffle()
        if (wordList.contains(currentWord)) getNextWord() else {
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordList.add(currentWord)
        }
    }

    fun nextWord() = if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
        getNextWord()
        true
    } else false

    fun isUserWordCorrect(playerWord: String) = if (playerWord.equals(currentWord, true)) {
        increaseScore()
        true
    } else false

    private fun increaseScore() {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordList.clear()
        getNextWord()
    }

    init {
        getNextWord()
    }
}
