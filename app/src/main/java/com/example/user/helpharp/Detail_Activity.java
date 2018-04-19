package com.example.user.helpharp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.example.user.helpharp.data.TabsContract;

public class Detail_Activity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private Uri mCurrentTabUri;
    private TextView name, tabs;
    private static final int EXISTING_LOADER = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        Intent intent = getIntent();
        mCurrentTabUri = intent.getData();
        name = (TextView) findViewById(R.id.editor_name);
        tabs = (TextView) findViewById(R.id.editor_tabs);
        getLoaderManager().initLoader(EXISTING_LOADER, null, this);


    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                TabsContract._ID,
                TabsContract.COLUMN_TAB_NAME,
                TabsContract.COLUMN_TABS};
        return new CursorLoader(this,   // Parent activity context
                mCurrentTabUri,         // Query the content URI for the current pet
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(TabsContract.COLUMN_TAB_NAME);
            int tabsColumnIndex = cursor.getColumnIndex(TabsContract.COLUMN_TABS);
            name.setText(cursor.getString(nameColumnIndex));
            tabs.setText(cursor.getString(tabsColumnIndex));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        name.setText("");
        tabs.setText("");
    }

}

