package com.example.user.helpharp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.helpharp.data.TabsContract;


public class CatalogActivity extends Activity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TABS_LOADER = 0;
    // Adapter for the ListView
    TabsCursorAdapter mCursorAdapter;
    AdapterView.AdapterContextMenuInfo info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        ListView tabListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the input_list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        tabListView.setEmptyView(emptyView);

        // Setup an Adapter to create a input_list item for each row of pet data in the Cursor.
        // There is no data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new TabsCursorAdapter(this, null);
        tabListView.setAdapter(mCursorAdapter);


        tabListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(CatalogActivity.this, Detail_Activity.class);
                Uri CurrentTabUri = ContentUris.withAppendedId(TabsContract.CONTENT_URI, id);
                intent.setData(CurrentTabUri);
                startActivity(intent);
            }
        });

        registerForContextMenu(tabListView);

        getLoaderManager().initLoader(TABS_LOADER, null, this);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                View view = info.targetView;
                TextView nameTextView = (TextView) view.findViewById(R.id.name);
                TextView summaryTextView = (TextView) view.findViewById(R.id.tab);
                String name = nameTextView.getText().toString();
                String tabs = summaryTextView.getText().toString();
                String textToIntent = name + "\n" + tabs;
                intent.putExtra(Intent.EXTRA_TEXT, textToIntent);
                startActivity(intent);
                return true;
            case R.id.delete:
                showDeleteConfirmationDialog();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    private void deleteAllTabs() {
        int rowsDeleted = getContentResolver().delete(TabsContract.CONTENT_URI, null, null);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                TabsContract._ID,
                TabsContract.COLUMN_TAB_NAME,
                TabsContract.COLUMN_TABS};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                TabsContract.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                TabsContract._ID + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteTab();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteTab() {
        Uri CurrentTabUri = ContentUris.withAppendedId(TabsContract.CONTENT_URI, info.id);
        getContentResolver().delete(CurrentTabUri, null, null);
    }


}