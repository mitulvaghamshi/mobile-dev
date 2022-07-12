package me.mitul.aij.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import me.mitul.aij.Bean.BeanAllClosingList;

public class HelperAllClosing extends SQLiteAssetHelper {

    @SuppressLint("SdCardPath")
    public HelperAllClosing(Context context) {
        super(context, "AIJ_DB.s3db", "/data/data/me.mitul.aij/databases", null, 1);
    }

    @SuppressLint({"NewApi", "DefaultLocale"})
    public ArrayList<BeanAllClosingList> selectClosingAll(int param_id_collage) {
        ArrayList<BeanAllClosingList> localArrayListClosing = new ArrayList<>();
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursorClosing = localSQLiteDatabase.rawQuery("SELECT OPENClosing, SEBCClosing, SCClosing, STClosing, EBCClosing, TFWSClosing FROM INS_Cutoff WHERE CollegeId = " + param_id_collage + ";", null);
        Cursor localBranchName = localSQLiteDatabase.rawQuery("SELECT BranchProperName FROM INS_Branch WHERE BranchID IN (SELECT BranchID FROM INS_Cutoff WHERE CollegeId = " + param_id_collage + ");", null);
        if (localCursorClosing.moveToFirst() && localBranchName.moveToFirst()) {
            do {
                BeanAllClosingList localBeanAllClosingList = new BeanAllClosingList();
                localBeanAllClosingList.setClosingBranchName(localBranchName.getString(localBranchName.getColumnIndex("BranchProperName")));
                localBeanAllClosingList.setClosingTfwsValue(localCursorClosing.getInt(localCursorClosing.getColumnIndex("TFWSClosing")));
                localBeanAllClosingList.setClosingOpenValue(localCursorClosing.getInt(localCursorClosing.getColumnIndex("OPENClosing")));
                localBeanAllClosingList.setClosingSebcValue(localCursorClosing.getInt(localCursorClosing.getColumnIndex("SEBCClosing")));
                localBeanAllClosingList.setClosingEbcValue(localCursorClosing.getInt(localCursorClosing.getColumnIndex("EBCClosing")));
                localBeanAllClosingList.setClosingScValue(localCursorClosing.getInt(localCursorClosing.getColumnIndex("SCClosing")));
                localBeanAllClosingList.setClosingStValue(localCursorClosing.getInt(localCursorClosing.getColumnIndex("STClosing")));
                localArrayListClosing.add(localBeanAllClosingList);
            } while (localCursorClosing.moveToNext() && localBranchName.moveToNext());
        }
        localBranchName.close();
        localCursorClosing.close();
        localSQLiteDatabase.close();
        return localArrayListClosing;
    }
}