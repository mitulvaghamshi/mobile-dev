package me.mitul.aij.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import me.mitul.aij.Bean.BeanCommon;

public class HelperHelpCenter extends SQLiteAssetHelper {
    @SuppressLint("SdCardPath")
    public HelperHelpCenter(Context context) {
        super(context, "AIJ_DB.s3db", "/data/data/me.mitul.aij/databases", null, 1);
    }

    public ArrayList<String> selectCityForHelpCenter() {
        ArrayList<String> localArrayList = new ArrayList<>();
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select distinct City from HelpCenter order by City", null);
        if (localCursor.moveToFirst())
            do localArrayList.add(localCursor.getString(localCursor.getColumnIndex("City")));
            while (localCursor.moveToNext());
        localCursor.close();
        localSQLiteDatabase.close();
        return localArrayList;
    }

    public ArrayList<BeanCommon> selectHelpCenter(String paramName) {
        ArrayList<BeanCommon> localArrayList = new ArrayList<>();
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select HelpCenterID, HelpCenterName, Address from HelpCenter where City = '" + paramName + "' order by HelpCenterName", null);
        if (localCursor.moveToFirst()) do {
            BeanCommon localBeanCollage = new BeanCommon();
            localBeanCollage.setCommonID(Integer.parseInt(localCursor.getString(localCursor.getColumnIndex("HelpCenterID"))));
            localBeanCollage.setCommonName(localCursor.getString(localCursor.getColumnIndex("HelpCenterName")));
            localBeanCollage.setCommonAddress(localCursor.getString(localCursor.getColumnIndex("Address")));
            localArrayList.add(localBeanCollage);
        } while (localCursor.moveToNext());
        localCursor.close();
        localSQLiteDatabase.close();
        return localArrayList;
    }
}