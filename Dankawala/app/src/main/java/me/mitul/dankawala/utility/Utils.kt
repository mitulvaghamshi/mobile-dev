package me.mitul.dankawala.utility

import android.app.ProgressDialog
import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class Utils(private val mContext: Context) {
    private val dbDir = File(
        Environment.getExternalStorageDirectory()
            .toString() + "/.dankawala_db"
    )
    private val sdDbFile = File(dbDir, DB_NAME)
    private val appDbFile: File = File(mContext.getDatabasePath(DB_NAME).toString())

    private fun copyFile(importFrom: File, exportTo: File) {
        try {
            FileInputStream(importFrom).channel.use { inChannel ->
                FileOutputStream(exportTo).channel.use { outChannel ->
                    inChannel.transferTo(0, inChannel.size(), outChannel)
                }
            }
        } catch (ignored: IOException) {
        }
    }

    val isBackupFound: Boolean
        get() = sdDbFile.exists()

    fun backupDB() {
        if (!dbDir.exists()) dbDir.mkdir()
        copyFile(appDbFile, sdDbFile)
    }

    fun restoreDb() {
        copyFile(sdDbFile, appDbFile)
    }

    val dialog: ProgressDialog
        get() {
            val mDialog = ProgressDialog(mContext)
            mDialog.setCanceledOnTouchOutside(false)
            mDialog.setMessage("Loading...")
            return mDialog
        }

    companion object {
        var DB_NAME = "dankawala.s3db"
        var DB_VERSION = 1
    }

}
