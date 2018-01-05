package com.example.user.helpharp;

import android.app.Activity;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.user.helpharp.data.TabsContract;


public class CatalogActivity extends Activity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TABS_LOADER = 0;

    /**
     * Adapter for the ListView
     */
    TabsCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);


        // Find the ListView which will be populated with the pet data
        ListView tabListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        tabListView.setEmptyView(emptyView);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        // There is no pet data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new TabsCursorAdapter(this, null);
        tabListView.setAdapter(mCursorAdapter);


        tabListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(CatalogActivity.this, Detail_Activity.class);

                // Form the content URI that represents the specific pet that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link PetEntry#CONTENT_URI}.
                // For example, the URI would be "content://com.example.android.pets/pets/2"
                // if the pet with ID 2 was clicked on.
                Uri currentPetUri = ContentUris.withAppendedId(TabsContract.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentPetUri);

                // Launch the {@link EditorActivity} to display the data for the current pet.
                startActivity(intent);
            }
        });


        getLoaderManager().initLoader(TABS_LOADER, null, this);


    }

    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    public void insertTAB(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.save_form);
        dialog.setTitle("Введите название:");
        dialog.show();
        final Button buttonOK = (Button) dialog.findViewById(R.id.save_form_bt_OK);
        final EditText name = (EditText) dialog.findViewById(R.id.save_form_et_name);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bdname = String.valueOf(name.getText());
                String bdtab = String.valueOf(MainActivity.enterTab.getText());
                ContentValues values = new ContentValues();
                values.put(TabsContract.COLUMN_TAB_NAME, bdname);
                values.put(TabsContract.COLUMN_TABS, bdtab);
                Uri newUri = getContentResolver().insert(TabsContract.CONTENT_URI, values);
                dialog.dismiss();
            }
        });
    }

    /**
     * Helper method to delete all pets in the database.
     */
    private void deleteAllTabs() {
        int rowsDeleted = getContentResolver().delete(TabsContract.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
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
                TabsContract._ID + " DESC");                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link PetCursorAdapter} with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}