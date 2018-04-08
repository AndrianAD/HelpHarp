package com.example.user.helpharp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class TabsProvider extends ContentProvider {
    public static final String LOG_TAG = TabsProvider.class.getSimpleName();
    private static final int TABS = 100;
    private static final int TAB_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    // Static initializer. This is run the first time anything is called from this class.
    static {
        sUriMatcher.addURI(TabsContract.CONTENT_AUTHORITY, TabsContract.PATH_TABS, TABS);
        sUriMatcher.addURI(TabsContract.CONTENT_AUTHORITY, TabsContract.PATH_TABS + "/#", TAB_ID);
    }
    private TabsDBHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new TabsDBHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case TABS:
                cursor = database.query(TabsContract.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case TAB_ID:
                selection = TabsContract._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(TabsContract.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TABS:
                return insertTab(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertTab(Uri uri, ContentValues values) {
        String name = values.getAsString(TabsContract.COLUMN_TAB_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Tab requires a name");
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(TabsContract.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        // Notify all listeners that the data has changed
        getContext().getContentResolver().notifyChange(uri, null);
        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TABS:
                return updateTab(uri, contentValues, selection, selectionArgs);
            case TAB_ID:
                selection = TabsContract._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateTab(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }


    private int updateTab(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(TabsContract.COLUMN_TAB_NAME)) {
            String name = values.getAsString(TabsContract.COLUMN_TAB_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(TabsContract.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TABS:
                rowsDeleted = database.delete(TabsContract.TABLE_NAME, selection, selectionArgs);
                break;
            case TAB_ID:
                selection = TabsContract._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(TabsContract.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TABS:
                return TabsContract.CONTENT_LIST_TYPE;
            case TAB_ID:
                return TabsContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
