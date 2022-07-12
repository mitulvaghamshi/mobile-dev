package me.mitul.aij.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class HelperAijExplorer extends SQLiteAssetHelper {

    @SuppressLint("SdCardPath")
    public HelperAijExplorer(Context context) {
        super(context, "AIJ_DB.s3db", "/data/data/me.mitul.aij/databases", null, 1);
    }

    @SuppressLint({"NewApi", "DefaultLocale"})
    public String selectHeader(int paramaId) {
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT TitleText FROM MST_Aij WHERE _id = " + paramaId, null);
        if (localCursor.moveToFirst())
            return localCursor.getString(localCursor.getColumnIndex("TitleText"));
        localCursor.close();
        localSQLiteDatabase.close();
        return "AIJ - Under Construction";
    }

    @SuppressLint({"NewApi", "DefaultLocale"})
    public String selectDetail(int paramaId) {
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT DetailText FROM MST_Aij WHERE _id = " + paramaId, null);
        if (localCursor.moveToFirst())
            return localCursor.getString(localCursor.getColumnIndex("DetailText"));
        localCursor.close();
        localSQLiteDatabase.close();
        return "AIJ - Under Construction";
    }
}