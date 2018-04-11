package com.example.user.helpharp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import static com.example.user.helpharp.MainActivity.check_difference_position;

public class ScaleActivity extends Activity {
    TextView major, minor, blues, penta_minor, penta_major;
    static Harp harp = new Harp();
    static int out_spiner1 = 5;
    static int out_spiner2 = 5;
    private int[] allNotes_id_Rihter = {R.id.b10, R.id.b40, R.id.b30, R.id.b00, R.id.b11, R.id.b51, R.id.b41, R.id.b31, R.id.b62, R.id.b52, R.id.b42, R.id.b32, R.id.b13,
            R.id.b43, R.id.b33, R.id.b03, R.id.b14, R.id.b34, R.id.b04, R.id.b15, R.id.b45, R.id.b35, R.id.b05, R.id.b36, R.id.b16,
            R.id.b46, R.id.b37, R.id.b07, R.id.b17, R.id.b38, R.id.b08, R.id.b18, R.id.b48, R.id.b39, R.id.b009, R.id.b09, R.id.b19, R.id.b49};

    private int[] allNotes_id_Cantri = allNotes_id_Rihter;

    private static final Map<String, Integer> scaleNote = new HashMap<String, Integer>() {
        {
            put("G", 0);
            put("Ab", 1);
            put("A", 2);
            put("Bb", 3);
            put("B", 4);
            put("C", 5);
            put("C#", 6);
            put("D", 7);
            put("Eb", 8);
            put("E", 9);
            put("F", 10);
            put("F#", 11);
        }
    };
    final private int[] majorScale = {2, 2, 1, 2, 2, 2, 1};
    final private int[] minorScale = {2, 1, 2, 2, 1, 2, 2};
    final private int[] bluesScale = {3, 2, 1, 1, 3, 2};
    final private int[] pentamajorScale = {2, 2, 3, 2, 3};
    final private int[] pentaminorScale = {3, 2, 2, 3, 2};
    private CheckBox switchCheckBox_minor, switchCheckBox_blues, switchCheckBox_penta_minor, switchCheckBox_major, switchCheckBox_penta_major;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scale_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        harp.makeharp("Рихтеровская", out_spiner1);


        major = (TextView) findViewById(R.id.gamma_major);
        minor = (TextView) findViewById(R.id.gamma_minor);
        blues = (TextView) findViewById(R.id.gamma_blues);
        penta_minor = (TextView) findViewById(R.id.penta_minor);
        penta_major = (TextView) findViewById(R.id.penta_major);
        switchCheckBox_major = (CheckBox) findViewById(R.id.switch_check_box_major);
        switchCheckBox_blues = (CheckBox) findViewById(R.id.switch_check_box_blues);
        switchCheckBox_penta_major = (CheckBox) findViewById(R.id.checkBox_penta_major);
        switchCheckBox_penta_minor = (CheckBox) findViewById(R.id.checkBox_penta_minor);
        switchCheckBox_minor = (CheckBox) findViewById(R.id.switch_check_box_minor);
        switchCheckBox_major.setChecked(true);
        show_harmonica();


        // -------Spiner1
        final Spinner left_spinner = (Spinner) findViewById(R.id.spinner1);
// Настраиваем адаптер`
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.harmonica_key, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Вызываем адаптер
        left_spinner.setAdapter(adapter);
        left_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.harmonica_key);
                String mychoose = choose[selectedItemPosition];
                if (scaleNote.containsKey(mychoose)) ;
                out_spiner1 = scaleNote.get(mychoose);
                harp.makeharp(harp.stroi, out_spiner1);
                getAllScales();
                show_harmonica();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // -------Spiner2
        final Spinner right_spinner = (Spinner) findViewById(R.id.spinner2);
// Настраиваем адаптер
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Вызываем адаптер
        right_spinner.setAdapter(adapter);
        right_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.harmonica_key);
                String mychoose = choose[selectedItemPosition];
                if (scaleNote.containsKey(mychoose)) ;
                out_spiner2 = scaleNote.get(mychoose);
                getAllScales();
                show_harmonica();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        // -------Spiner stroi
        final Spinner spiner_stroi = (Spinner) findViewById(R.id.spinner_stroi);
        adapter = ArrayAdapter.createFromResource(this, R.array.harmonica_stroi, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner_stroi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.harmonica_stroi);
                if (harp.stroi != "Рихтеровская") {
                    harp.makeharp("Рихтеровская", out_spiner1);
                }
                if (choose[selectedItemPosition].equals("Рихтеровская")) {
                    harp.stroi = "Рихтеровская";
                    harp.makeharp("Рихтеровская", out_spiner1);
                    getAllScales();
                }
                if (choose[selectedItemPosition].equals("Падди")) {
                    harp.stroi = "Падди";
                    harp.makeharp("Падди", out_spiner1);
                    getAllScales();
                }
                if (choose[selectedItemPosition].equals("Кантри")) {
                    harp.stroi = "Кантри";
                    harp.makeharp("Кантри", out_spiner1);
                    getAllScales();

                }
                if (choose[selectedItemPosition].equals("Нат. Минор")) {
                    harp.stroi = "Нат. Минор";
                    harp.makeharp("Нат. Минор", out_spiner1);
                    getAllScales();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });





//        // Чекбокс мажор
        switchCheckBox_major.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (harp.allnote.isEmpty()) {
                    return;
                }
                makeScale(isChecked, majorScale, major);
            }
        });

// Чекбокс мінор
        switchCheckBox_minor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (harp.allnote.isEmpty()) {
                    return;
                }
                makeScale(isChecked, minorScale, minor);
            }
        });

// Чекбокс блюз
        switchCheckBox_blues.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (harp.allnote.isEmpty()) {
                    return;
                }
                makeScale(isChecked, bluesScale, blues);
            }
        });

        // Чекбокс минорная пентатоника
        switchCheckBox_penta_minor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (harp.allnote.isEmpty()) {
                    return;
                }
                makeScale(isChecked, pentaminorScale, penta_minor);
            }
        });

        // Чекбокс мажорная пентатоника
        switchCheckBox_penta_major.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (harp.allnote.isEmpty()) {
                    return;
                }
                makeScale(isChecked, pentamajorScale, penta_major);
            }
        });
    }


    //----------------------------------------------------------
    public String makeScale(boolean isChecked, int[] scale_array, TextView resultView) {
        int j = 0, temp;
        String firstString, secondString = "";
        StringBuilder stringBuilder = new StringBuilder();
        int difference_position = check_difference_position(harp.position, out_spiner2);
        for (int i = difference_position; i > 0; i = i - temp) {
            temp = scale_array[j + scale_array.length - 1];
            if (i - temp < 0) {
                break;
            }
            Hole nots = (Hole) harp.allnote.get(i - temp);
            j--;
            stringBuilder.insert(0, isChecked ? nots.getNote() + " " : nots.getTabs() + " ");
        }
        firstString = String.valueOf(stringBuilder);

        Hole nots = (Hole) harp.allnote.get(difference_position);
        String firsTonica = isChecked ? nots.getNote() + " " : nots.getTabs();
        nots = (Hole) harp.allnote.get(difference_position + 12);
        String secondTonica = isChecked ? nots.getNote() + " " : new String(" " + nots.getTabs() + " ");
        nots = (Hole) harp.allnote.get(difference_position + 24);
        String thirdTonica = isChecked ? nots.getNote() + " " : new String(" " + nots.getTabs() + " ");
        int lenght = firsTonica.length();
        int lenght2 = secondTonica.length();
        int lenght3 = thirdTonica.length();

        j = 0;
        for (int i = 0; i < 37 - difference_position; i = i + temp) {
            temp = scale_array[j];
            nots = (Hole) harp.allnote.get(i + difference_position);
            j++;
            if (j == scale_array.length) {
                j = 0;
            }
            secondString += isChecked ? nots.getNote() + " " : nots.getTabs() + " ";
        }
        String fullstring = firstString + secondString;
        int indexTonica = isChecked ? fullstring.indexOf(firsTonica) : fullstring.indexOf(firsTonica + " ");
        int indexTonica2 = isChecked ? fullstring.indexOf(secondTonica, indexTonica + 1) : fullstring.indexOf(secondTonica);
        int indexTonica3 = isChecked ? fullstring.indexOf(thirdTonica, indexTonica2 + 1) : fullstring.indexOf(thirdTonica);

        SpannableStringBuilder sb = new SpannableStringBuilder(fullstring);
        sb.setSpan(new ForegroundColorSpan(Color.RED), indexTonica, indexTonica + lenght, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        SpannableStringBuilder sb2 = new SpannableStringBuilder(sb);
        sb2.setSpan(new ForegroundColorSpan(Color.BLUE), indexTonica2, indexTonica2 + lenght2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        SpannableStringBuilder sb3 = new SpannableStringBuilder(sb2);
        sb3.setSpan(new ForegroundColorSpan(Color.MAGENTA), indexTonica3, indexTonica3 + lenght3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        resultView.setText(sb3);
        return fullstring;
    }

    //----------------------------------------------------------
    private void getAllScales() {
        makeScale(false, majorScale, major);
        makeScale(false, minorScale, minor);
        makeScale(false, bluesScale, blues);
        makeScale(false, pentaminorScale, penta_minor);
        makeScale(false, pentamajorScale, penta_major);
    }

    private void show_harmonica() {
        String all_Notes = harp.printlist(harp.allnote, false);
        String all_Tabs = harp.printlist(harp.allnote, true);
        String array_of_Notes[] = all_Notes.split(" ");
        String array_of_allTabs[] = all_Tabs.split(" ");
        String major_gama = makeScale(false, majorScale, major);
        TextView textView;
        textView = (TextView) findViewById(R.id.b12);
        textView.setText(array_of_Notes[7]);
        for (int i = 0; i < allNotes_id_Rihter.length; i++) {
            textView = (TextView) findViewById(allNotes_id_Rihter[i]);
            textView.setText(array_of_Notes[i]);
        }
        int iterator = 0;
        CheckBox[] arrayCheckBox = {switchCheckBox_major, switchCheckBox_minor, switchCheckBox_blues, switchCheckBox_penta_minor, switchCheckBox_penta_major};
        String curent_scale[] = get_scale(if_checkBox_is_Cheked(arrayCheckBox));
        for (int x = 0; x < curent_scale.length; x++) {
            for (int y = iterator; y < allNotes_id_Rihter.length; y++) {
                textView = (TextView) findViewById(allNotes_id_Rihter[y]);
                String curentString = (String) textView.getText();
                if (curent_scale[x].equals(curentString)) {
                    textView.setTextColor(Color.parseColor("#cc0000"));
                    iterator = y;
                    break;
                }


            }

        }

    }


    private CheckBox if_checkBox_is_Cheked(CheckBox[] arrayCheckBox) {
        CheckBox checkBox = null;
        for (int i = 0; i < arrayCheckBox.length; i++) {
            checkBox = arrayCheckBox[i];
            if (checkBox.isChecked()) {
                return checkBox;
            }
        }
        return null;
    }

    private String[] get_scale(CheckBox checkBox) {
        String scale = "";
        String[] array_scale = null;
        if (checkBox.getId() == R.id.switch_check_box_major) {
            scale = makeScale(true, majorScale, major);
        }
        if (checkBox.getId() == R.id.switch_check_box_minor) {
            scale = makeScale(true, majorScale, minor);
        }
        array_scale = scale.split(" ");
        return array_scale;
    }


}
