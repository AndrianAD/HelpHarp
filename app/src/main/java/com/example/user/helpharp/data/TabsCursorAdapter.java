package com.example.user.helpharp.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.user.helpharp.R;

public class TabsCursorAdapter extends CursorAdapter {

    public TabsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.tab);

        // Find the columns of tab attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(TabsContract.COLUMN_TAB_NAME);
        int tabsColumnIndex = cursor.getColumnIndex(TabsContract.COLUMN_TABS);

        // Read the pet attributes from the Cursor for the current tab
        String name = cursor.getString(nameColumnIndex);
        String tab = cursor.getString(tabsColumnIndex);

        // Update the TextViews with the attributes for the current tab
        nameTextView.setText(name);
        summaryTextView.setText(tab);
    }
}
