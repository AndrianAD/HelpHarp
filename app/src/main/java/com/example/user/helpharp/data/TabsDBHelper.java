package com.example.user.helpharp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TabsDBHelper extends SQLiteOpenHelper {

    public final String LOG_TAG = TabsDBHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "helptabs.db";
    private static final int DATABASE_VERSION = 1;

    public TabsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_TABS_TABLE = "CREATE TABLE " + TabsContract.TABLE_NAME + " ("
                + TabsContract.COLUMN_TAB_NAME + " TEXT NOT NULL, "
                + TabsContract.COLUMN_TABS + " TEXT);";
        db.execSQL(SQL_CREATE_TABS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}


