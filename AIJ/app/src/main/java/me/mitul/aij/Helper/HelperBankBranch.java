package me.mitul.aij.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import me.mitul.aij.Bean.BeanCommon;

public class HelperBankBranch extends SQLiteAssetHelper {
    @SuppressLint("SdCardPath")
    public HelperBankBranch(Context context) {
        super(context, "AIJ_DB.s3db", "/data/data/me.mitul.dankawala/databases", null, 1);
    }

    public ArrayList<String> selectCityForBankBranch() {

        ArrayList<String> localArrayList = new ArrayList<String>();
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select distinct City from BankBranch order by City", null);
        if (localCursor.moveToFirst()) do
            localArrayList.add(localCursor.getString(localCursor.getColumnIndex("City")));
        while (localCursor.moveToNext());
        localCursor.close();
        localSQLiteDatabase.close();
        return localArrayList;
    }

    public ArrayList<BeanCommon> selectBankBranch(String paramName) {
        ArrayList<BeanCommon> localArrayList = new ArrayList<BeanCommon>();
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select BankBranchID, BankBranchName, Address from BankBranch where City = '" + paramName + "' order by BankBranchName", null);
        if (localCursor.moveToFirst()) do {
            BeanCommon localBeanCollage = new BeanCommon();
            localBeanCollage.setCommonID(Integer.parseInt(localCursor.getString(localCursor.getColumnIndex("BankBranchID"))));
            localBeanCollage.setCommonName(localCursor.getString(localCursor.getColumnIndex("BankBranchName")));
            localBeanCollage.setCommonAddress(localCursor.getString(localCursor.getColumnIndex("Address")));
            localArrayList.add(localBeanCollage);
        } while (localCursor.moveToNext());
        localCursor.close();
        localSQLiteDatabase.close();
        return localArrayList;
    }
}
