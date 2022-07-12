package com.example.unscramble

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.unscramble.databinding.FragmentGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GameFragment : Fragment() {
    private val viewModel: GameViewModel by viewModels()
    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameViewModel = viewModel
        binding.maxNoOfWords = MAX_NO_OF_WORDS
        binding.lifecycleOwner = viewLifecycleOwner

        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
    }

    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(
                if (viewModel.score.value!! > (MAX_NO_OF_WORDS / 2)) "Congratulations!"
                else "Better luck next time!"
            )
            .setMessage(getString(R.string.you_scored, viewModel.score.value))
            .setNegativeButton(getString(R.string.exit)) { _, _ -> activity?.finish() }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                viewModel.reinitializeData()
                false.setErrorTextField()
            }.setCancelable(false).show()
    }

    private fun onSubmitWord() {
        val playerWord = binding.textInputEditText.text.toString()
        if (viewModel.isUserWordCorrect(playerWord)) {
            false.setErrorTextField()
            if (!viewModel.nextWord()) showFinalScoreDialog()
        } else true.setErrorTextField()
    }

    private fun onSkipWord() {
        if (viewModel.nextWord()) false.setErrorTextField()
        else showFinalScoreDialog()
    }

    private fun Boolean.setErrorTextField() {
        binding.textField.isErrorEnabled = this
        if (this) {
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.error = null
            binding.textInputEditText.setText("")
        }
    }
}
