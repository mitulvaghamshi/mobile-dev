package me.mitul.aij.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import me.mitul.aij.Bean.BeanBranch;

public class HelperBranch extends SQLiteAssetHelper {
    @SuppressLint("SdCardPath")
    public HelperBranch(Context context) {
        super(context, "AIJ_DB.s3db", "/data/data/me.mitul.aij/databases", null, 1);
    }

    @SuppressLint({"NewApi", "DefaultLocale"})
    public ArrayList<BeanBranch> selectAllBranch() {
        ArrayList<BeanBranch> localArrayList = new ArrayList<>();
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("Select BranchID,BranchName,BranchShortName FROM INS_Branch ORDER BY Sequence", null);

        if (localCursor.moveToFirst()) do {
            BeanBranch localBeanCollage = new BeanBranch();
            localBeanCollage.setBranchId(localCursor.getInt(localCursor.getColumnIndex("BranchID")));
            localBeanCollage.setBranchName(localCursor.getString(localCursor.getColumnIndex("BranchName")) + "(" + localCursor.getString(localCursor.getColumnIndex("BranchShortName")) + ")");
            Cursor localCollegeNum = localSQLiteDatabase.rawQuery("SELECT CollegeID FROM INS_Cutoff WHERE BranchID = " + localBeanCollage.getBranchId() + ";", null);
            if (localCollegeNum.moveToFirst()) {
                int num1111 = 0;
                do num1111++;
                while (localCollegeNum.moveToNext());
                localBeanCollage.setNumCollege(num1111);
            }
            localCollegeNum.close();
            localArrayList.add(localBeanCollage);
        } while (localCursor.moveToNext());
        localCursor.close();
        localSQLiteDatabase.close();
        return localArrayList;
    }
}