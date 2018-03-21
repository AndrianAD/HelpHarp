package com.example.user.helpharp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
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


public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener {
    public static Harp userHarp = new Harp();
    public static Harp needHarp = new Harp();
    CustomKeyboard mCustomKeyboard;
    public static ArrayList<String> input_list = new ArrayList<String>();
    public static ArrayList<Integer> tempArray = new ArrayList<>();
    static EditText enterTab;
    static TextView result;
    public static Hole final_tabs;
    static int octava_set = 0;
    private TabsDBHelper dbHelper;
    private SeekBar seekBar;
    private TextView need_harm_key_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mCustomKeyboard = new CustomKeyboard(this, R.id.keyboardview, R.xml.keyboard);
        mCustomKeyboard.registerEditText(R.id.edit_text_enter_tabl);
        result = (TextView) findViewById(R.id.text_view_result);
        enterTab = (EditText) findViewById(R.id.edit_text_enter_tabl);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        need_harm_key_view = (TextView) findViewById(R.id.view_z);
        seekBar.setOnSeekBarChangeListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            enterTab.setShowSoftInputOnFocus(false); // hide the standart keyboard
        }
        dbHelper = new TabsDBHelper(this);

        start_activity();
    }


    public void start_activity() {
        final Button my_harm_key, need_harm_key, actionCount, btncopy_enter, btncopy_result;
        ImageButton save_enterTabs, save_resultTabs;
        final TextView my_harm_key_view = (TextView) findViewById(R.id.view_n);
        my_harm_key = (Button) findViewById(R.id.button_my_harm_key);
        need_harm_key = (Button) findViewById(R.id.need_harm_key);
//        actionCount
//                = (Button) findViewById(R.id.button_action_count);
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


//        btncopy_result = (Button) findViewById(R.id.button_copy2);
//        btncopy_result.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String text = result.getText().toString();
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


        save_enterTabs = (ImageButton) findViewById(R.id.enter_save);
        save_enterTabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_form(enterTab.getText().toString());
            }
        });


        save_resultTabs = (ImageButton) findViewById(R.id.result_save);
        save_resultTabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_form(result.getText().toString());
            }
        });






        final ImageButton octava_plus = (ImageButton) findViewById(R.id.octava_plus);
        final ImageButton octava_minus = (ImageButton) findViewById(R.id.octava_minus);

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
                        changetabs(userHarp, needHarp);
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
                        changetabs(userHarp, needHarp);
                    } else
                        return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


//        actionCount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calculate(userHarp, needHarp);
//            }
//        });


//-----------------------------------------------
        my_harm_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_chose_key(userHarp, my_harm_key_view, true);

            }
        });

//-----------------------------------------------
        need_harm_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_chose_key(needHarp, need_harm_key_view, false);
            }
        });

//-----------------------------------------------
//        RESET!
        ImageButton reset = (ImageButton) findViewById(R.id.reset_id);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(R.string.restart_message, R.string.Ok, R.string.cancel);
            }
        });
//-----------------------------------------------
        // -------Spiner
        final Spinner needHarp = (Spinner) findViewById(R.id.spinner2);
// Настраиваем адаптер
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.harmonica_stroi, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Вызываем адаптер
        needHarp.setAdapter(adapter);
        needHarp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                String[] choose = getResources().getStringArray(R.array.harmonica_stroi);

                if (choose[selectedItemPosition].equals("Рихтеровская")) {
                    MainActivity.needHarp.stroi = "Рихтеровская";
                    MainActivity.needHarp.makeharp(MainActivity.needHarp.stroi, MainActivity.needHarp.position, need_harm_key_view);
                    calculate(userHarp, MainActivity.needHarp);

                }
                if (choose[selectedItemPosition].equals("Падди")) {
                    MainActivity.needHarp.stroi = "Падди";
                    MainActivity.needHarp.makeharp(MainActivity.needHarp.stroi, MainActivity.needHarp.position, need_harm_key_view);
                    calculate(userHarp, MainActivity.needHarp);

                }
                if (choose[selectedItemPosition].equals("Кантри")) {
                    MainActivity.needHarp.stroi = "Кантри";
                    MainActivity.needHarp.makeharp(MainActivity.needHarp.stroi, MainActivity.needHarp.position, need_harm_key_view);
                    calculate(userHarp, MainActivity.needHarp);

                }
                if (choose[selectedItemPosition].equals("Нат. Минор")) {
                    MainActivity.needHarp.stroi = "Нат. Минор";
                    MainActivity.needHarp.makeharp(MainActivity.needHarp.stroi, MainActivity.needHarp.position, need_harm_key_view);
                    calculate(userHarp, MainActivity.needHarp);

                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//-----------------------------------------------
        // -------Spiner
        final Spinner userSpiner = (Spinner) findViewById(R.id.spinner1);
// Вызываем адаптер
        userSpiner.setAdapter(adapter);
        userSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.harmonica_stroi);
                if (choose[selectedItemPosition].equals("Рихтеровская")) {
                    userHarp.stroi = "Рихтеровская";
                    userHarp.makeharp(userHarp.stroi, userHarp.position, my_harm_key_view);
                    calculate(userHarp, MainActivity.needHarp);
                }
                if (choose[selectedItemPosition].equals("Падди")) {
                    userHarp.stroi = "Падди";
                    userHarp.makeharp(userHarp.stroi, userHarp.position, my_harm_key_view);
                    calculate(userHarp, MainActivity.needHarp);
                }
                if (choose[selectedItemPosition].equals("Кантри")) {
                    userHarp.stroi = "Кантри";
                    userHarp.makeharp(userHarp.stroi, userHarp.position, my_harm_key_view);
                    calculate(userHarp, MainActivity.needHarp);
                }
                if (choose[selectedItemPosition].equals("Нат. Минор")) {
                    userHarp.stroi = "Нат. Минор";
                    userHarp.makeharp(userHarp.stroi, userHarp.position, my_harm_key_view);
                    calculate(userHarp, MainActivity.needHarp);
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void save_form(final String text) {
        if (!text.equals("")) {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.save_form);
            dialog.setTitle("Введите название:");
            dialog.show();
            final Button buttonOK = (Button) dialog.findViewById(R.id.save_form_bt_OK);
            final EditText name = (EditText) dialog.findViewById(R.id.save_form_et_name);
            name.post(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager inputMethodManager =
                            (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInputFromWindow(
                            name.getApplicationWindowToken(), InputMethodManager.SHOW_IMPLICIT, 0);
                    name.requestFocus();
                }
            });
            buttonOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String bdname = String.valueOf(name.getText());
                    ContentValues values = new ContentValues();
                    values.put(TabsContract.COLUMN_TAB_NAME, bdname);
                    values.put(TabsContract.COLUMN_TABS, text);
                    Uri newUri = getContentResolver().insert(TabsContract.CONTENT_URI, values);
                    dialog.dismiss();
                    OpenList(null);
                }
            });
        }
        if (text.equals("")) {
            Toast toast = Toast.makeText(MainActivity.this, "Для начала введите табы!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
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
            get_input_tabs(get_tab);
            changetabs(harp1, harp2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changetabs(Harp harp1, Harp harp2) {
        try {
            tempArray.clear();
            String rezultat = "";
            int temp = check_difference_position(harp1.position, harp2.position);
            for (int i = 0; i < input_list.size(); i++) {
                String list_i = input_list.get(i);
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

    public static int check_difference_position(int n, int z) {
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

//    @Override
//    public void onBackPressed() {
//        // NOTE Trap the back key: when the CustomKeyboard is still visible hide it, only when it is invisible, finish activity
//        if (mCustomKeyboard.isCustomKeyboardVisible())
//            mCustomKeyboard.hideCustomKeyboard();
//        else this.finish();
//    }


    public void dialog_chose_key(final Harp harp, final View view, final boolean if_userHarp) {
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
                calculate(userHarp, needHarp);
                dialog.dismiss();
                if (!if_userHarp) {
                    seekBar.setProgress(check_difference_position(userHarp.position, needHarp.position));
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
    public static void get_input_tabs(String inputtabs) {
        String str[] = inputtabs.split(" ");
        int i = 0;
        do {
            if (str[i].equals("3") && userHarp.stroi != "Падди") {
                str[i] = "-2";
            }
            i++;
        }
        while (i != str.length);
        input_list = new ArrayList<>(Arrays.asList(str));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        progress = seekBar.getProgress();
        int position = userHarp.position + progress;
        if (userHarp.position + progress >= 12) {
            position = Math.abs(12 - position);
        }
        needHarp.makeharp(needHarp.stroi, position);
        need_harm_key_view.setText(needHarp.key_of_harp);
        calculate(userHarp, needHarp);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }


    public void showDeleteConfirmationDialog(int message, int positive, int negative) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = getIntent();
                finish();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });
        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
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

    public void OpenList(View view) {
        Intent myIntent = new Intent(MainActivity.this, CatalogActivity.class);
        startActivity(myIntent);
    }
}