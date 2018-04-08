package com.example.user.helpharp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TabsDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "helptabs.db";
    private static final int DATABASE_VERSION = 1;

    public TabsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    String SQL_CREATE_TABS_TABLE = "CREATE TABLE " + TabsContract.TABLE_NAME + " ("
            + TabsContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TabsContract.COLUMN_TAB_NAME + " TEXT NOT NULL, "
            + TabsContract.COLUMN_TABS + " TEXT);";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // ALTER TABLE
//        if (oldVersion < 2) {
//            db.execSQL(do somethings);
//        }
//        if (oldVersion < 3) {
//            db.execSQL(do somethings);
//        }
    }


}


