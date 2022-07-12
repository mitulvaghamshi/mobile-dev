package me.mitul.dankawala.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import me.mitul.dankawala.utility.Utils

class LoginHelper @SuppressLint("SdCardPath")
constructor(context: Context?) : SQLiteAssetHelper(
    context,
    Utils.DB_NAME,
    "/data/data/me.mady.dankawala/databases",
    null,
    Utils.DB_VERSION
) {
    fun attemptLogin(paramName: String, paramPass: String): Int {
        val mQuery =
            "SELECT login_id,login_username,login_password FROM master_login WHERE login_username = '$paramName' AND login_password = '$paramPass'"
        val mSQLiteDatabase = readableDatabase
        val mCursor = mSQLiteDatabase.rawQuery(mQuery, null)
        if (mCursor.count < 0) return 999
        if (mCursor.moveToFirst()) {
            val mUsername = mCursor.getString(mCursor.getColumnIndex("login_username"))
            val mPassword = mCursor.getString(mCursor.getColumnIndex("login_password"))
            return if (mUsername == paramName && mPassword == paramPass) mCursor.getInt(
                mCursor.getColumnIndex(
                    "login_id"
                )
            ) else 999
        }
        mCursor.close()
        mSQLiteDatabase.close()
        return 999
    }

    fun insertUser(paramName: String?, paramPass: String?, paramKeep: Int): Long {
        val mSQLiteDatabase = writableDatabase
        val mValues = ContentValues()
        mValues.put("login_username", paramName)
        mValues.put("login_password", paramPass)
        mValues.put("login_keep", paramKeep)
        val mID = mSQLiteDatabase.insert("master_login", null, mValues)
        mValues.clear()
        mSQLiteDatabase.close()
        return mID
    }

    init {
        writableDatabase.close()
    }
}