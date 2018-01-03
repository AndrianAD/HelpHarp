package com.example.user.helpharp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class TabsContract implements BaseColumns {

    public TabsContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.user.helpharp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TABS = "db_tabs";
    public final static String TABLE_NAME = "db_tabs";
    public final static String COLUMN_TAB_NAME = "name";
    public final static String COLUMN_TABS = "tabs";
    public final static String _ID = BaseColumns._ID;


    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TABS);


    public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TABS;

    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TABS;

}
