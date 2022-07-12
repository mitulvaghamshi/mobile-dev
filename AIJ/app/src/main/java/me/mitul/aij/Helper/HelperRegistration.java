package me.mitul.aij.Helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HelperRegistration extends SQLiteAssetHelper {
    Context myContext;

    @SuppressLint("SdCardPath")
    public HelperRegistration(Context context) {
        super(context, "AIJ_DB.s3db", "/data/data/me.mitul.aij/databases", null, 1);
        myContext = context;
    }

    public boolean attemtReg(String... paramData) {
        String localQuery = "SELECT * FROM MST_User WHERE UserName = '" + paramData[0] + "' AND Password = '" + paramData[4] + "'";
        SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
        final Cursor localCursor = localSQLiteDatabase.rawQuery(localQuery, null);
        if (!localCursor.moveToFirst()) {
            ContentValues regValues = new ContentValues();
            //regValues.put("_id", "null");
            regValues.put("DeviceID", paramData[6]);
            regValues.put("UserName", paramData[0]);
            regValues.put("Email", paramData[1]);
            regValues.put("Mobile", paramData[2]);
            regValues.put("City", paramData[3]);
            regValues.put("Password", paramData[4]);
            regValues.put("DateOfReg", new SimpleDateFormat("dd-MM-yyyy ", Locale.ENGLISH).format(new Date()));
            regValues.put("Extra", "extra");
            localSQLiteDatabase.insert("ACC_Registration", null, regValues);
            new HelperLogin(myContext).insertUser(paramData[0], paramData[4]);
            return true;
        }
        localCursor.close();
        localSQLiteDatabase.close();
        return false;
    }
}