/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.workmanager

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.example.workmanager.worker.BlurWorker
import com.example.workmanager.worker.CleanupWorker
import com.example.workmanager.worker.SaveWorker

class BlurViewModel(application: Application) : ViewModel() {
    private var imageUri: Uri? = null
    internal var outputUri: Uri? = null

    internal val outputWorkInfo: LiveData<List<WorkInfo>>

    private val workManager = WorkManager.getInstance(application)

    init {
        imageUri = getImageUri(application.applicationContext)
        outputWorkInfo = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
    }

    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let { builder.putString(KEY_IMAGE_URI, imageUri.toString()) }
        return builder.build()
    }

    internal fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    internal fun applyBlur(blurLevel: Int) {
        // Add WorkRequest to Cleanup temporary images
        var continuation = workManager.beginUniqueWork(
            IMAGE_MANIPULATION_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequestBuilder<CleanupWorker>().build()
        )

        for (i in 0 until blurLevel) {
            val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()
            if (i == 0) {
                blurBuilder.setInputData(createInputDataForUri()).build()
            }
            // Add WorkRequest to blur the image
            continuation = continuation.then(blurBuilder.build())
        }

        val chargingConstraints = Constraints.Builder()
            .setRequiresCharging(true).build()
        // Add WorkRequest to save the image to the filesystem
        val saveBuilder = OneTimeWorkRequestBuilder<SaveWorker>()
            .setConstraints(chargingConstraints).addTag(TAG_OUTPUT)

        continuation = continuation.then(saveBuilder.build())

        // Actually start the work
        continuation.enqueue()
    }

    private fun uriOrNull(uriString: String?) =
        if (uriString.isNullOrEmpty()) null else Uri.parse(uriString)

    private fun getImageUri(context: Context) = with(context.resources) {
        Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(getResourcePackageName(R.drawable.android_party))
            .appendPath(getResourceTypeName(R.drawable.android_party))
            .appendPath(getResourceEntryName(R.drawable.android_party))
            .build()
    }

    internal fun setOutputUri(outputImageUri: String?) {
        outputUri = uriOrNull(outputImageUri)
    }

    class BlurViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BlurViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BlurViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
