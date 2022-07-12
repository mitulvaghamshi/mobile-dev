package me.mitul.aij.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import me.mitul.aij.Bean.BeanCollage;

public class HelperCollage extends SQLiteAssetHelper {
    @SuppressLint("SdCardPath")
    public HelperCollage(Context context) {
        super(context, "AIJ_DB.s3db", "/data/data/me.mitul.aij/databases", null, 1);
    }

    @SuppressLint({"NewApi", "DefaultLocale"})
    public ArrayList<BeanCollage> selectAllCollage() {
        ArrayList<BeanCollage> localArrayList = new ArrayList<>();
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("Select CollegeID,CollegeShortName,Fees,Hostel from INS_College", null);
        if (localCursor.moveToFirst()) do {
            BeanCollage localBeanCollage = new BeanCollage();
            localBeanCollage.setCollageId(localCursor.getInt(localCursor.getColumnIndex("CollegeID")));
            localBeanCollage.setCollageName(localCursor.getString(localCursor.getColumnIndex("CollegeShortName")));
            localBeanCollage.setFees(localCursor.getInt(localCursor.getColumnIndex("Fees")) + "/-");
            localBeanCollage.setHostel(localCursor.getString(localCursor.getColumnIndex("Hostel")));
            Cursor cursorBNAME = localSQLiteDatabase.rawQuery("SELECT BranchShortName FROM INS_Branch WHERE BranchID IN (SELECT BranchID FROM INS_Intake WHERE CollegeID = " + localBeanCollage.getCollageId() + ");", null);
            if (cursorBNAME.moveToFirst()) {
                String branches = "";
                do
                    branches = branches.concat((cursorBNAME.getString(cursorBNAME.getColumnIndex("BranchShortName"))) + ",");
                while (cursorBNAME.moveToNext());
                localBeanCollage.setBranches(branches.substring(0, branches.length() - 1));
            }
            cursorBNAME.close();
            localArrayList.add(localBeanCollage);
        } while (localCursor.moveToNext());
        localCursor.close();
        localSQLiteDatabase.close();
        return localArrayList;
    }

    @SuppressLint({"NewApi", "DefaultLocale"})
    public ArrayList<BeanCollage> selectBranchWiseCollage(int br_id) {
        ArrayList<BeanCollage> localSelectedArrayList = new ArrayList<>();
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT CollegeID,CollegeShortName,Fees,Hostel FROM INS_College WHERE CollegeID IN (SELECT CollegeID FROM INS_Cutoff WHERE BranchID = " + br_id + ");", null);
        if (localCursor.moveToFirst()) do {
            BeanCollage localBeanCollage = new BeanCollage();
            localBeanCollage.setCollageId(localCursor.getInt(localCursor.getColumnIndex("CollegeID")));
            localBeanCollage.setCollageName(localCursor.getString(localCursor.getColumnIndex("CollegeShortName")));
            localBeanCollage.setFees(localCursor.getInt(localCursor.getColumnIndex("Fees")) + "/-");
            localBeanCollage.setHostel(localCursor.getString(localCursor.getColumnIndex("Hostel")));
            Cursor cursorBNAME = localSQLiteDatabase.rawQuery("SELECT BranchShortName FROM INS_Branch WHERE BranchID IN (SELECT BranchID FROM INS_Intake WHERE CollegeID = " + localBeanCollage.getCollageId() + ");", null);
            if (cursorBNAME.moveToFirst()) {
                String branches = "";
                do
                    branches = branches.concat((cursorBNAME.getString(cursorBNAME.getColumnIndex("BranchShortName"))) + ",");
                while (cursorBNAME.moveToNext());
                localBeanCollage.setBranches(branches.substring(0, branches.length() - 1));
            }
            cursorBNAME.close();
            localSelectedArrayList.add(localBeanCollage);
        } while (localCursor.moveToNext());
        localCursor.close();
        localSQLiteDatabase.close();
        return localSelectedArrayList;
    }

    @SuppressLint({"NewApi", "DefaultLocale"})
    public ArrayList<BeanCollage> selectUniversityWiseCollage(int uni_id) {
        ArrayList<BeanCollage> localSelectedArrayList = new ArrayList<>();
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT CollegeID,CollegeShortName,Fees,Hostel FROM INS_College WHERE CollegeID IN (SELECT CollegeID FROM INS_College WHERE UniversityID = " + uni_id + ");", null);

        if (localCursor.moveToFirst()) do {
            BeanCollage localBeanCollage = new BeanCollage();
            localBeanCollage.setCollageId(localCursor.getInt(localCursor.getColumnIndex("CollegeID")));
            localBeanCollage.setCollageName(localCursor.getString(localCursor.getColumnIndex("CollegeShortName")));
            localBeanCollage.setFees(localCursor.getInt(localCursor.getColumnIndex("Fees")) + "/-");
            localBeanCollage.setHostel(localCursor.getString(localCursor.getColumnIndex("Hostel")));
            Cursor cursorBNAME = localSQLiteDatabase.rawQuery("SELECT BranchShortName FROM INS_Branch WHERE BranchID IN (SELECT BranchID FROM INS_Intake WHERE CollegeID = " + localBeanCollage.getCollageId() + ");", null);
            if (cursorBNAME.moveToFirst()) {
                String branches = "";
                do
                    branches = branches.concat((cursorBNAME.getString(cursorBNAME.getColumnIndex("BranchShortName"))) + ",");
                while (cursorBNAME.moveToNext());
                localBeanCollage.setBranches(branches.substring(0, branches.length() - 1));
            }
            cursorBNAME.close();
            localSelectedArrayList.add(localBeanCollage);
        } while (localCursor.moveToNext());
        localCursor.close();
        localSQLiteDatabase.close();
        return localSelectedArrayList;
    }

    @SuppressLint({"NewApi", "DefaultLocale"})
    public ArrayList<BeanCollage> selectIntakeById(int paramClgId) {
        ArrayList<BeanCollage> localArrayListIntake = new ArrayList<>();
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor_Seat;
        Cursor localCursor_Vecent;
        Cursor localCursorIntake = localSQLiteDatabase.rawQuery("select INS_Intake.CollegeID,INS_Intake.Shift,INS_Branch.BranchProperName,INS_Intake.Intake,CASE WHEN INS_Intake.Vacant is null then ' ' ELSE INS_Intake.Vacant END as Vacant from INS_Intake Inner join INS_Branch on INS_Intake.BranchID=INS_Branch.BranchID Where INS_Intake.CollegeID = " + paramClgId + " AND INS_Intake.Shift = 1 order by INS_Branch.BranchProperName", null);
        if (localCursorIntake.moveToFirst()) {
            do {
                BeanCollage localBeanIntakee = new BeanCollage();
                localBeanIntakee.setLvBranch(localCursorIntake.getString(localCursorIntake.getColumnIndex("BranchProperName")));
                localBeanIntakee.setLvSeat(localCursorIntake.getInt(localCursorIntake.getColumnIndex("Intake")));
                localBeanIntakee.setLvVecent(localCursorIntake.getInt(localCursorIntake.getColumnIndex("Vacant")));
                localCursor_Seat = localSQLiteDatabase.rawQuery("SELECT Intake,Vacant from INS_Intake WHERE BranchID = (SELECT BranchID FROM INS_Intake WHERE CollegeID = " + paramClgId + ");", null);
                localCursor_Vecent = localSQLiteDatabase.rawQuery("SELECT Vacant from INS_Intake WHERE BranchID = (SELECT BranchID FROM INS_Intake WHERE CollegeID = " + paramClgId + ");", null);
                if (localCursor_Seat.moveToFirst())
                    localBeanIntakee.setLvSeat(localCursor_Seat.getInt(localCursor_Seat.getColumnIndex("Intake")));
                localBeanIntakee.setLvVecent(localCursor_Seat.getInt(localCursor_Seat.getColumnIndex("Vacant")));
                if (localCursor_Vecent.moveToFirst())
                    localBeanIntakee.setLvVecent(localCursor_Vecent.getInt(localCursor_Vecent.getColumnIndex("Vacant")));
                localArrayListIntake.add(localBeanIntakee);
                localCursor_Seat.moveToNext();
                localCursor_Vecent.moveToNext();
            } while (localCursorIntake.moveToNext());
            localCursor_Seat.close();
            localCursor_Vecent.close();
        }
        localCursorIntake.close();
        localSQLiteDatabase.close();
        return localArrayListIntake;
    }

    @SuppressLint({"NewApi", "DefaultLocale"})
    public BeanCollage selectCollageByID(int paramId) {
        BeanCollage localBeanDetailCollageList = new BeanCollage();
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("Select * from INS_College where CollegeID = " + paramId, null);
        if (localCursor.moveToFirst()) {
            localBeanDetailCollageList.setLabel(localCursor.getString(localCursor.getColumnIndex("CollegeCode")));
            localBeanDetailCollageList.setClgCollageID(localCursor.getInt(localCursor.getColumnIndex("CollegeID")));
            localBeanDetailCollageList.setClgSortName(localCursor.getString(localCursor.getColumnIndex("CollegeShortName")));
            localBeanDetailCollageList.setClgFullName(localCursor.getString(localCursor.getColumnIndex("CollegeName")));
            localBeanDetailCollageList.setClgAddres(localCursor.getString(localCursor.getColumnIndex("Address")));
            localBeanDetailCollageList.setPhone(localCursor.getString(localCursor.getColumnIndex("Phone")));
            localBeanDetailCollageList.setWeb(localCursor.getString(localCursor.getColumnIndex("Website")));
            localBeanDetailCollageList.setEmail(localCursor.getString(localCursor.getColumnIndex("Email")));
            localBeanDetailCollageList.setFees(localCursor.getString(localCursor.getColumnIndex("Fees")));
            localBeanDetailCollageList.setType(localCursor.getString(localCursor.getColumnIndex("CollegeTypeID")));
            localBeanDetailCollageList.setHostel(localCursor.getString(localCursor.getColumnIndex("Hostel")));
            localBeanDetailCollageList.setUniversity(localCursor.getString(localCursor.getColumnIndex("UniversityID")));
        }
        Cursor localCursorType = localSQLiteDatabase.rawQuery("Select CollegeTypeName from MST_CollegeType where CollegeTypeID = " + localBeanDetailCollageList.getType(), null);
        if (localCursorType.moveToFirst())
            localBeanDetailCollageList.setType(localCursorType.getString(localCursorType.getColumnIndex("CollegeTypeName")));
        Cursor localCursorUniversity = localSQLiteDatabase.rawQuery("Select UniversityShortName from MST_University where UniversityID = " + localBeanDetailCollageList.getUniversity(), null);
        if (localCursorUniversity.moveToFirst())
            localBeanDetailCollageList.setUniversity(localCursorUniversity.getString(localCursorUniversity.getColumnIndex("UniversityShortName")));
        localCursorUniversity.close();
        localCursorType.close();
        localCursor.close();
        localSQLiteDatabase.close();
        return localBeanDetailCollageList;
    }
}
