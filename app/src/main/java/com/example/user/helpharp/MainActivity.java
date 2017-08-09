package com.example.user.helpharp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    String stroi1 = "Рихтеровская";
    String stroi2 = "Рихтеровская";
    int position = 5;
    Harp harp1 = new Harp();
    Harp harp2 = new Harp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        start_activity();

    }


    public void start_activity() {
        Button my_harm_key, need_harm_key, actionCount, btncopy, btncopy2, newactivity;
        final TextView my_harm_key_view = (TextView) findViewById(R.id.view_n);
        final TextView need_harm_key_view = (TextView) findViewById(R.id.view_z);
        TextView result;
        EditText enterTab;
        my_harm_key = (Button) findViewById(R.id.button_my_harm_key);
        need_harm_key = (Button) findViewById(R.id.need_harm_key);
        enterTab = (EditText) findViewById(R.id.edit_text_enter_tabl);
        actionCount = (Button) findViewById(R.id.button_action_count);
        result = (TextView) findViewById(R.id.text_view_result);

        CustomKeyboard mCustomKeyboard;
        mCustomKeyboard = new CustomKeyboard(this, R.id.keyboardview, R.xml.hexkbd);
        mCustomKeyboard.registerEditText(R.id.edit_text_enter_tabl);


        my_harm_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                harp1.makeharp(stroi1,position,my_harm_key_view);
            }
        });


        need_harm_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                harp1.makeharp(stroi1,position,need_harm_key_view);
            }
        });



        // -------Spiner
        // Получаем экземпляр элемента Spinner
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
                    stroi2 = "Рихтеровская";

                }
                if (choose[selectedItemPosition].equals("Падди")) {
                    stroi2 = "Падди";

                }
                if (choose[selectedItemPosition].equals("Кантри")) {
                    stroi2 = "Кантри";

                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//-----------------------------------------------

        // -------Spiner
        // Получаем экземпляр элемента Spinner
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
// Вызываем адаптер
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.harmonica_stroi);
                if (choose[selectedItemPosition].equals("Рихтеровская")) {
                    stroi1 = "Рихтеровская";
                }
                if (choose[selectedItemPosition].equals("Падди")) {
                    stroi1 = "Падди";
                }
                if (choose[selectedItemPosition].equals("Кантри")) {
                    stroi1 = "Кантри";
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//        ---------------------------------------



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
