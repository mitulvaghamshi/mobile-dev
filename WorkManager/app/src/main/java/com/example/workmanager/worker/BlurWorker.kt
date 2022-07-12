package com.example.workmanager.worker

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.workmanager.KEY_IMAGE_URI

private const val TAG = "BlurWorker"

class BlurWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val appContext = applicationContext
        val resourceUri = inputData.getString(KEY_IMAGE_URI)

        makeStatusNotification("Blurring Image", appContext)

        sleep()

        return try {
//            val picture = BitmapFactory.decodeResource(appContext.resources, R.drawable.cupcake)
            if (TextUtils.isEmpty(resourceUri)) {
                Log.e(TAG, "Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }
            val resolver = appContext.contentResolver
            val picture = BitmapFactory
                .decodeStream(resolver.openInputStream(Uri.parse(resourceUri)))
            val output = blurBitmap(picture, appContext)
            val outputUri = writeBitmapToFile(appContext, output)
            val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())
            makeStatusNotification("Output Uri: $outputUri", appContext)
            Result.success(outputData)
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error Applying Blur")
            throwable.printStackTrace()
            Result.failure()
        }
    }
}
