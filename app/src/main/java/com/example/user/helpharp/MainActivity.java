package com.example.user.helpharp;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.helpharp.data.TabsContract;
import com.example.user.helpharp.data.TabsDBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {
    public static Harp harp1 = new Harp();
    public static Harp harp2 = new Harp();
    CustomKeyboard mCustomKeyboard;
    public static ArrayList<String> list = new ArrayList<String>();
    public static ArrayList<Integer> tempArray = new ArrayList<>();
    static EditText enterTab;
    static TextView result;
    public static Hole final_tabs;
    static int octava_set = 0;
    private TabsDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mCustomKeyboard = new CustomKeyboard(this, R.id.keyboardview, R.xml.hexkbd);
        mCustomKeyboard.registerEditText(R.id.edit_text_enter_tabl);
        result = (TextView) findViewById(R.id.text_view_result);
        enterTab = (EditText) findViewById(R.id.edit_text_enter_tabl);
        enterTab.setShowSoftInputOnFocus(false); // hide the standart keyboard
        dbHelper = new TabsDBHelper(this);


        start_activity();
    }


    public void start_activity() {
        final Button my_harm_key, need_harm_key, actionCount, btncopy_enter, btncopy_result, newactivity;
        final TextView my_harm_key_view = (TextView) findViewById(R.id.view_n);
        final TextView need_harm_key_view = (TextView) findViewById(R.id.view_z);
        my_harm_key = (Button) findViewById(R.id.button_my_harm_key);
        need_harm_key = (Button) findViewById(R.id.need_harm_key);
        actionCount
                = (Button) findViewById(R.id.button_action_count);
        result.setMovementMethod(new ScrollingMovementMethod());

//        btncopy_enter = (Button) findViewById(R.id.button_copy);
//        btncopy_enter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String text = enterTab.getText().toString();
//                ClipboardManager clipboardManager;
//                ClipData clipData;
//                clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//                clipData = ClipData.newPlainText("text", text);
//                clipboardManager.setPrimaryClip(clipData);
//                Toast toast = Toast.makeText(getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
//            }
//        });


        btncopy_result = (Button) findViewById(R.id.button_copy2);
        btncopy_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = result.getText().toString();
                ClipboardManager clipboardManager;
                ClipData clipData;
                clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipData = ClipData.newPlainText("text", text);
                clipboardManager.setPrimaryClip(clipData);
                Toast toast = Toast.makeText(getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });


        newactivity = (Button) findViewById(R.id.button);
        newactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, Activity2.class);
                startActivity(myIntent);


            }
        });


        Button octava_plus = (Button) findViewById(R.id.octava_plus);
        final Button octava_minus = (Button) findViewById(R.id.octava_minus);

        octava_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int min = (int) Collections.min(tempArray);
                    if (octava_set >= 25) {
                        return;
                    }
                    if (min >= 12) {
                        octava_set = octava_set + 12;
                        changetabs(harp1, harp2);
                    } else
                        return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        octava_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int max = (int) Collections.max(tempArray);
                    if (max <= 25) {
                        octava_set = octava_set + (-12);
                        changetabs(harp1, harp2);
                    } else
                        return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        actionCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(harp1, harp2);
            }
        });


//-----------------------------------------------
        my_harm_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_chose_key(harp1, my_harm_key_view);

            }


        });

//-----------------------------------------------
        need_harm_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_chose_key(harp2, need_harm_key_view);
            }
        });

//-----------------------------------------------
//        RESET!
        Button reset = (Button) findViewById(R.id.reset_id);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.save_form);
                dialog.setTitle("Выберите тональность:");
                dialog.show();
                final Button buttonOK = (Button) dialog.findViewById(R.id.save_form_bt_OK);
                final EditText name = (EditText) dialog.findViewById(R.id.save_form_et_name);
                buttonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String bdname = String.valueOf(name.getText());
                        String bdtab = String.valueOf(enterTab.getText());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(TabsContract.COLUMN_TAB_NAME, bdname);
                        values.put(TabsContract.COLUMN_TABS, bdtab);
                        long newRowid = db.insert(TabsContract.TABLE_NAME, null, values);
                        Log.v("Catalog activity", "new rowid" + newRowid);
                        dialog.dismiss();
                        displayDatabaseInfo();
                    }
                });


//                Intent intent = getIntent();
//                finish();
//                overridePendingTransition(0, 0);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);


            }
        });
//-----------------------------------------------
        // -------Spiner
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
// Настраиваем адаптер
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.harmonica_stroi, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Вызываем адаптер
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                String[] choose = getResources().getStringArray(R.array.harmonica_stroi);

                if (choose[selectedItemPosition].equals("Рихтеровская")) {
                    harp2.stroi = "Рихтеровская";
                    harp2.makeharp(harp2.stroi, harp2.position, need_harm_key_view);
                    calculate(harp1, harp2);

                }
                if (choose[selectedItemPosition].equals("Падди")) {
                    harp2.stroi = "Падди";
                    harp2.makeharp(harp2.stroi, harp2.position, need_harm_key_view);
                    calculate(harp1, harp2);

                }
                if (choose[selectedItemPosition].equals("Кантри")) {
                    harp2.stroi = "Кантри";
                    harp2.makeharp(harp2.stroi, harp2.position, need_harm_key_view);
                    calculate(harp1, harp2);

                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//-----------------------------------------------
        // -------Spiner
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
// Вызываем адаптер
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.harmonica_stroi);
                if (choose[selectedItemPosition].equals("Рихтеровская")) {
                    harp1.stroi = "Рихтеровская";
                    harp1.makeharp(harp1.stroi, harp1.position, my_harm_key_view);
                    calculate(harp1, harp2);
                }
                if (choose[selectedItemPosition].equals("Падди")) {
                    harp1.stroi = "Падди";
                    harp1.makeharp(harp1.stroi, harp1.position, my_harm_key_view);
                    calculate(harp1, harp2);
                }
                if (choose[selectedItemPosition].equals("Кантри")) {
                    harp1.stroi = "Кантри";
                    harp1.makeharp(harp1.stroi, harp1.position, my_harm_key_view);
                    calculate(harp1, harp2);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
//-----------------------------------------------


    public static void calculate(Harp harp1, Harp harp2) {
        try {
            if (harp1.allnote.isEmpty() || harp2.allnote.isEmpty()) {
                return;
            }
            String get_tab = (enterTab.getText().toString());
            octava_set = 0;
            get_tab = get_tab.replace("\n", " \n ");
            input_tabs(get_tab);
            changetabs(harp1, harp2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changetabs(Harp harp1, Harp harp2) {
        try {
            tempArray.clear();
            String rezultat = "";
            int temp = check_temp(harp1.position, harp2.position);
            for (int i = 0; i < list.size(); i++) {
                String list_i = list.get(i);
                if (list_i.contains("\n"))
                    rezultat += "\n";
                for (int j = 0; j < 38; j++) {
                    String list_J = ((Hole) harp1.allnote.get(j)).getTabs();
                    // Ищет совпадения в первом List
                    if (list_J.equals(list_i)) {
                        int peremennaia = j + temp;
                        if (peremennaia < 0) {
                            peremennaia = peremennaia + 12;
                        }
                        if (peremennaia > 37) {
                            peremennaia = peremennaia - 12;
                        }
                        tempArray.add(peremennaia - octava_set);
                        final_tabs = (Hole) harp2.allnote.get(peremennaia - octava_set);
                        rezultat += " " + final_tabs.getTabs();
                        break;
                    }
                }
            }
            result.setText(rezultat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int check_temp(int n, int z) {
        int temps = z - n;
        int[] nums = {-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11};
        for (int i : nums) {
            if (temps == i) {
                temps = 12 - (-i);
                break;
            }
        }
        return temps;
    }

    @Override
    public void onBackPressed() {
        // NOTE Trap the back key: when the CustomKeyboard is still visible hide it, only when it is invisible, finish activity
        if (mCustomKeyboard.isCustomKeyboardVisible())
            mCustomKeyboard.hideCustomKeyboard();
        else this.finish();
    }


    public void dialog_chose_key(final Harp harp, final View view) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.button_key);
        dialog.setTitle("Выберите тональность:");
        dialog.show();
        final Button button0 = (Button) dialog.findViewById(R.id.button0);
        final Button button1 = (Button) dialog.findViewById(R.id.button1);
        final Button button2 = (Button) dialog.findViewById(R.id.button2);
        final Button button3 = (Button) dialog.findViewById(R.id.button3);
        final Button button4 = (Button) dialog.findViewById(R.id.button4);
        final Button button5 = (Button) dialog.findViewById(R.id.button5);
        final Button button6 = (Button) dialog.findViewById(R.id.button6);
        final Button button7 = (Button) dialog.findViewById(R.id.button7);
        final Button button8 = (Button) dialog.findViewById(R.id.button8);
        final Button button9 = (Button) dialog.findViewById(R.id.button9);
        final Button button10 = (Button) dialog.findViewById(R.id.button10);
        final Button button11 = (Button) dialog.findViewById(R.id.button11);
        final Map<Integer, Integer> mapNotes = new HashMap<Integer, Integer>() {
            {
                put(R.id.button0, 0);
                put(R.id.button1, 1);
                put(R.id.button2, 2);
                put(R.id.button3, 3);
                put(R.id.button4, 4);
                put(R.id.button5, 5);
                put(R.id.button6, 6);
                put(R.id.button7, 7);
                put(R.id.button8, 8);
                put(R.id.button9, 9);
                put(R.id.button10, 10);
                put(R.id.button11, 11);
            }
        };
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = v.getId();
                if (mapNotes.containsKey(temp)) ;
                int note = mapNotes.get(temp);
                harp.position = note;
                harp.makeharp(harp.stroi, harp.position, (TextView) view);
                calculate(harp1, harp2);
                dialog.dismiss();
            }
        };
        button0.setOnClickListener(onClickListener);
        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        button3.setOnClickListener(onClickListener);
        button4.setOnClickListener(onClickListener);
        button5.setOnClickListener(onClickListener);
        button6.setOnClickListener(onClickListener);
        button7.setOnClickListener(onClickListener);
        button8.setOnClickListener(onClickListener);
        button9.setOnClickListener(onClickListener);
        button10.setOnClickListener(onClickListener);
        button11.setOnClickListener(onClickListener);
    }

    // Ввод исходных табов от пользователя
    public static void input_tabs(String inputtabs) {
        String str[] = inputtabs.split(" ");

        int i = 0;

        do {
            if (str[i].equals("3") && harp1.stroi != "Падди") {
                str[i] = "-2";
            }
            i++;
        }
        while (i != str.length);

        list = new ArrayList<>(Arrays.asList(str));
    }




    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                TabsContract.COLUMN_TAB_NAME,
                TabsContract.COLUMN_TABS};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                TabsContract.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

//        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            result.append(TabsContract.COLUMN_TAB_NAME + " -------- " +
                    TabsContract.COLUMN_TABS + "\n");

            // Figure out the index of each column
            int tabsColumnIndex = cursor.getColumnIndex(TabsContract.COLUMN_TABS);
            int nameColumnIndex = cursor.getColumnIndex(TabsContract.COLUMN_TAB_NAME);


            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                String currentTabs = cursor.getString(tabsColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                result.append(("\n" + currentName + " - " +
                        currentTabs));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

}



