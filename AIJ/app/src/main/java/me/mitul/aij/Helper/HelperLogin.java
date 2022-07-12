package me.mitul.aij.Helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class HelperLogin extends SQLiteAssetHelper {
    @SuppressLint("SdCardPath")
    public HelperLogin(Context context) {
        super(context, "AIJ_DB.s3db", "/data/data/me.mitul.aij/databases", null, 1);
        getReadableDatabase().close();
    }

    public int attemtLogin(String paramName, String paramPass) {
        String localQuery = "SELECT * FROM MST_User WHERE UserName = '" + paramName + "' AND Password = '" + paramPass + "'";
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery(localQuery, null);
        if (localCursor.getCount() < 0) return 999;
        if (localCursor.moveToFirst()) {
            String localName = localCursor.getString(localCursor.getColumnIndex("UserName"));
            String localPass = localCursor.getString(localCursor.getColumnIndex("Password"));
            return localName.equals(paramName) && localPass.equals(paramPass) ? localCursor.getInt(localCursor.getColumnIndex("UserID")) : 999;
        }
        localCursor.close();
        localSQLiteDatabase.close();
        return 999;
    }

    public void insertUser(String paramName, String paramPass) {
        SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put("UserName", paramName);
        userValues.put("Password", paramPass);
        localSQLiteDatabase.insert("MST_User", null, userValues);
        localSQLiteDatabase.close();
    }
}
