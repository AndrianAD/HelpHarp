package com.example.user.helpharp;

import android.app.Dialog;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {
    Harp harp1 = new Harp();
    Harp harp2 = new Harp();
    CustomKeyboard mCustomKeyboard;
    public static ArrayList<String> list = new ArrayList<String>();
    public static ArrayList<Integer> tempArray = new ArrayList<>();
    EditText enterTab;
    TextView result;
    public static Hole final_tabs;
    int temp;
    static int octava_set = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mCustomKeyboard = new CustomKeyboard(this, R.id.keyboardview, R.xml.hexkbd);
        mCustomKeyboard.registerEditText(R.id.edit_text_enter_tabl);
        result = (TextView) findViewById(R.id.text_view_result);
        enterTab = (EditText) findViewById(R.id.edit_text_enter_tabl);


        start_activity();


    }


    public void start_activity() {
        Button my_harm_key, need_harm_key, actionCount, btncopy, btncopy2, newactivity;
        final TextView my_harm_key_view = (TextView) findViewById(R.id.view_n);
        final TextView need_harm_key_view = (TextView) findViewById(R.id.view_z);
        my_harm_key = (Button) findViewById(R.id.button_my_harm_key);
        need_harm_key = (Button) findViewById(R.id.need_harm_key);
        actionCount = (Button) findViewById(R.id.button_action_count);


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
                    harp2.stroi = "Рихтеровская";

                }
                if (choose[selectedItemPosition].equals("Падди")) {
                    harp2.stroi = "Падди";

                }
                if (choose[selectedItemPosition].equals("Кантри")) {
                    harp2.stroi = "Кантри";

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
                    harp1.stroi = "Рихтеровская";
                }
                if (choose[selectedItemPosition].equals("Падди")) {
                    harp1.stroi = "Падди";
                }
                if (choose[selectedItemPosition].equals("Кантри")) {
                    harp1.stroi = "Кантри";
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    } /*end of start activity*/

    private void calculate(Harp harp1, Harp harp2) {
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

    public void changetabs(Harp harp1, Harp harp2) {
        try {
            tempArray.clear();
            String rezultat = "";
            temp = check_temp(harp1.position, harp2.position);
            for (int i = 0; i < list.size(); i++) {
                String list_i = list.get(i);
                if (list_i.contains("\n"))
                    rezultat += "\n";
                for (int j = 0; j < 38; j++) {
                    Hole hole = (Hole) harp1.allnote.get(j);
                    String list_J = hole.getTabs();
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

    private int check_temp(int n, int z) {
        int temp = z - n;
        int[] nums = {-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11};
        for (int i : nums) {
            if (temp == i) {
                temp = 12 - (-i);
                break;
            }
        }
        return temp;
    }



    @Override
    public void onBackPressed() {
        // NOTE Trap the back key: when the CustomKeyboard is still visible hide it, only when it is invisible, finish activity
        if (mCustomKeyboard.isCustomKeyboardVisible()) mCustomKeyboard.hideCustomKeyboard();
        else this.finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void dialog_chose_key(final Harp harp, final View view) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.button_key);
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
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button0:
                        harp.position = 0;
                        harp.makeharp(harp.stroi, harp.position, (TextView) view);
                        dialog.dismiss();
                        break;

                    case R.id.button1:
                        harp.position = 1;
                        harp.makeharp(harp.stroi, harp.position, (TextView) view);
                        dialog.dismiss();
                        break;

                    case R.id.button2:
                        harp.position = 2;
                        harp.makeharp(harp.stroi, harp.position, (TextView) view);
                        dialog.dismiss();
                        break;

                    case R.id.button3:
                        harp.position = 3;
                        harp.makeharp(harp.stroi, harp.position, (TextView) view);
                        dialog.dismiss();
                        break;

                    case R.id.button4:
                        harp.position = 4;
                        harp.makeharp(harp.stroi, harp.position, (TextView) view);
                        dialog.dismiss();
                        break;
                    case R.id.button5:
                        harp.position = 5;
                        harp.makeharp(harp.stroi, harp.position, (TextView) view);
                        dialog.dismiss();
                        break;
                    case R.id.button6:
                        harp.position = 6;
                        harp.makeharp(harp.stroi, harp.position, (TextView) view);
                        dialog.dismiss();
                        break;
                    case R.id.button7:
                        harp.position = 7;
                        harp.makeharp(harp.stroi, harp.position, (TextView) view);
                        dialog.dismiss();
                        break;
                    case R.id.button8:
                        harp.position = 8;
                        harp.makeharp(harp.stroi, harp.position, (TextView) view);
                        dialog.dismiss();
                        break;

                    case R.id.button9:
                        harp.position = 9;
                        harp.makeharp(harp.stroi, harp.position, (TextView) view);
                        dialog.dismiss();
                        break;
                    case R.id.button10:
                        harp.position = 10;
                        harp.makeharp(harp.stroi, harp.position, (TextView) view);
                        dialog.dismiss();
                        break;
                    case R.id.button11:
                        harp.position = 11;
                        harp.makeharp(harp.stroi, harp.position, (TextView) view);
                        dialog.dismiss();
                        break;
                }
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
    public void input_tabs(String inputtabs) {
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
        String temp = "";
        for (String s : list) {
            temp = temp + s;
        }
        result.setText(temp);
    }




}
