package com.example.workmanager

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import com.example.workmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: BlurViewModel by viewModels {
        BlurViewModel.BlurViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.goButton.setOnClickListener { viewModel.applyBlur(blurLevel) }
        binding.cancelButton.setOnClickListener { viewModel.cancelWork() }
        binding.seeFileButton.setOnClickListener { seeFile() }
        viewModel.outputWorkInfo.observe(this, workInfoObserver())
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun seeFile() {
        viewModel.outputUri?.let {
            val actionView = Intent(Intent.ACTION_VIEW, it)
            actionView.resolveActivity(packageManager)?.run {
                startActivity(actionView)
            }
        }
    }

    private fun workInfoObserver(): Observer<List<WorkInfo>> {
        return Observer {
            if (it.isNullOrEmpty()) return@Observer
            val workInfo = it[0]
            if (workInfo.state.isFinished) {
                showWorkFinished()
                val outputImageUri = workInfo.outputData.getString(KEY_IMAGE_URI)
                if (!outputImageUri.isNullOrEmpty()) {
                    viewModel.setOutputUri(outputImageUri)
                    binding.seeFileButton.visibility = View.VISIBLE
                }
            } else {
                showWorkInProgress()
            }
        }
    }

    /**
     * Shows and hides views for when the Activity is processing an image
     */
    private fun showWorkInProgress() = with(binding) {
        progressBar.visibility = View.VISIBLE
        cancelButton.visibility = View.VISIBLE
        goButton.visibility = View.GONE
        seeFileButton.visibility = View.GONE
    }

    /**
     * Shows and hides views for when the Activity is done processing an image
     */
    private fun showWorkFinished() = with(binding) {
        progressBar.visibility = View.GONE
        cancelButton.visibility = View.GONE
        goButton.visibility = View.VISIBLE
    }

    private val blurLevel
        get() = when (binding.radioBlurGroup.checkedRadioButtonId) {
            R.id.radio_blur_lv_1 -> 1
            R.id.radio_blur_lv_2 -> 2
            R.id.radio_blur_lv_3 -> 3
            else -> 1
        }
}
