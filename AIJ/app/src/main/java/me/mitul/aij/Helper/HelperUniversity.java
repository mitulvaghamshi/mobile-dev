package me.mitul.aij.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import me.mitul.aij.Bean.BeanUniversity;

public class HelperUniversity extends SQLiteAssetHelper {
    @SuppressLint("SdCardPath")
    public HelperUniversity(Context context) {
        super(context, "AIJ_DB.s3db", "/data/data/me.mitul.aij/databases", null, 1);
    }

    @SuppressLint({"NewApi", "DefaultLocale"})
    public ArrayList<BeanUniversity> selectAllUniversity() {
        ArrayList<BeanUniversity> localArrayList = new ArrayList<BeanUniversity>();
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select UniversityID, UniversityName from MST_University order by UniversityName", null);
        if (localCursor.moveToFirst()) do {
            BeanUniversity localBeanUniversity = new BeanUniversity();
            localBeanUniversity.setUniversityID(localCursor.getInt(localCursor.getColumnIndex("UniversityID")));
            localBeanUniversity.setUniversityName(localCursor.getString(localCursor.getColumnIndex("UniversityName")));
            localArrayList.add(localBeanUniversity);
        } while (localCursor.moveToNext());
        localCursor.close();
        localSQLiteDatabase.close();
        return localArrayList;
    }

    @SuppressLint({"NewApi", "DefaultLocale"})
    public BeanUniversity selectUniversityByID(int paramId) {
        BeanUniversity localBeanDetailUniversityList = new BeanUniversity();
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select UniversityID,UniversityName,UniversityShortName,Address,Website,Email,Phone,UniversityType from MST_University where UniversityID=" + paramId, null);
        if (localCursor.moveToFirst()) {
            localBeanDetailUniversityList.setUniversityID(localCursor.getInt(localCursor.getColumnIndex("UniversityID")));
            localBeanDetailUniversityList.setUniversityShortName(localCursor.getString(localCursor.getColumnIndex("UniversityShortName")));
            localBeanDetailUniversityList.setUniversityName(localCursor.getString(localCursor.getColumnIndex("UniversityName")));
            localBeanDetailUniversityList.setUniversityAddress(localCursor.getString(localCursor.getColumnIndex("Address")));
            localBeanDetailUniversityList.setUniversityWebsite(localCursor.getString(localCursor.getColumnIndex("Website")));
            localBeanDetailUniversityList.setUniversityEmail(localCursor.getString(localCursor.getColumnIndex("Email")));
            localBeanDetailUniversityList.setUniversityPhone(localCursor.getString(localCursor.getColumnIndex("Phone")));
            localBeanDetailUniversityList.setUniversityType(localCursor.getString(localCursor.getColumnIndex("UniversityType")));
        }
        localCursor.close();
        localSQLiteDatabase.close();
        return localBeanDetailUniversityList;
    }
}