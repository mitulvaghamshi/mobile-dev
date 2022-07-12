package me.mitul.aij.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import me.mitul.aij.Bean.BeanSplashScreen;

public class HelperSplashScreen extends SQLiteAssetHelper {

    public HelperSplashScreen(Context context) {
        super(context, "AIJ_DB.s3db", "/data/data/me.mitul.aij/databases", null, 1);
    }

    public BeanSplashScreen selectSplashTextData(int paramaId) {
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT SplashText, TextSize, TextColor FROM MST_Splash WHERE _id = " + paramaId, null);
        BeanSplashScreen localBeanSplashScreen = new BeanSplashScreen();
        if (localCursor.moveToFirst()) {
            localBeanSplashScreen.setSplashText(localCursor.getString(localCursor.getColumnIndex("SplashText")));
            localBeanSplashScreen.setSplashTextSize(localCursor.getString(localCursor.getColumnIndex("TextSize")));
            localBeanSplashScreen.setSplashTextColor(localCursor.getString(localCursor.getColumnIndex("TextColor")));
            return localBeanSplashScreen;
        }
        localCursor.close();
        localSQLiteDatabase.close();
        return null;
    }
}
