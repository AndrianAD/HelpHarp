package com.example.user.helpharp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import static com.example.user.helpharp.MainActivity.check_temp;

public class Activity2 extends Activity {
    TextView major, minor, blues, penta_minor, penta_major;
    static String gammaview = " ";
    Harp harp = new Harp();
    Harp harp2 = new Harp();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        harp.makeharp("Рихтеровская", 5);
        harp2.makeharp("Рихтеровская", 5);

        major = (TextView) findViewById(R.id.gamma_major);
        minor = (TextView) findViewById(R.id.gamma_minor);
        blues = (TextView) findViewById(R.id.gamma_blues);
        penta_minor = (TextView) findViewById(R.id.penta_minor);
        penta_major = (TextView) findViewById(R.id.penta_major);


        // -------Spiner
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
// Настраиваем адаптер
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.harmonica_key, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Вызываем адаптер
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.harmonica_key);

                if (choose[selectedItemPosition].equals("G")) {
                    harp2.makeharp("Рихтеровская", 0);
                }
                if (choose[selectedItemPosition].equals("Ab")) {
                    harp2.makeharp("Рихтеровская", 1);
                }
                if (choose[selectedItemPosition].equals("A")) {
                    harp2.makeharp("Рихтеровская", 2);
                }
                if (choose[selectedItemPosition].equals("Bb")) {
                    harp2.makeharp("Рихтеровская", 3);
                }
                if (choose[selectedItemPosition].equals("B")) {
                    harp2.makeharp("Рихтеровская", 4);
                }
                if (choose[selectedItemPosition].equals("C")) {
                    harp2.makeharp("Рихтеровская", 5);
                }
                if (choose[selectedItemPosition].equals("C#")) {
                    harp2.makeharp("Рихтеровская", 6);
                }
                if (choose[selectedItemPosition].equals("D")) {
                    harp2.makeharp("Рихтеровская", 7);
                }
                if (choose[selectedItemPosition].equals("Eb")) {
                    harp2.makeharp("Рихтеровская", 8);
                }
                if (choose[selectedItemPosition].equals("E")) {
                    harp2.makeharp("Рихтеровская", 9);
                }
                if (choose[selectedItemPosition].equals("F")) {
                    harp2.makeharp("Рихтеровская", 10);
                }
                if (choose[selectedItemPosition].equals("F#")) {
                    harp2.makeharp("Рихтеровская", 11);
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//
//        // Чекбокс мажор
        CheckBox switchCheckBox_major = (CheckBox) findViewById(R.id.switch_check_box_major);
        switchCheckBox_major.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (harp.allnote.isEmpty()) {
                    return;
                }

                getGamma_major(isChecked);
            }
        });

// Чекбокс мінор
        CheckBox switchCheckBox_minor = (CheckBox) findViewById(R.id.switch_check_box_minor);
        switchCheckBox_minor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (harp.allnote.isEmpty()) {
                    return;
                }
                getGamma_minor(isChecked);
            }
        });

// Чекбокс блюз
        CheckBox switchCheckBox_blues = (CheckBox) findViewById(R.id.switch_check_box_blues);
        switchCheckBox_blues.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (harp.allnote.isEmpty()) {
                    return;
                }
                getGamma_blues(isChecked);
            }
        });

        // Чекбокс минорная пентатоника
        CheckBox switchCheckBox_penta_minor = (CheckBox) findViewById(R.id.checkBox_penta_minor);
        switchCheckBox_penta_minor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (harp.allnote.isEmpty()) {
                    return;
                }
                get_minor_pentatonic(isChecked);
            }
        });

        // Чекбокс мажорная пентатоника
        CheckBox switchCheckBox_penta_major = (CheckBox) findViewById(R.id.checkBox_penta_major);
        switchCheckBox_penta_major.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (harp.allnote.isEmpty()) {
                    return;
                }
                get_major_pentatonic(isChecked);
            }
        });





    }

    public void getGamma_major(boolean isChecked) {
        int j = 0;
        int int_masiv = 0;
        int temp = check_temp(harp.position, harp2.position);
        final int[] masiv = {2, 2, 1, 2, 2, 2, 1};
        for (int i = 0; i < 37 - temp; i = i + int_masiv) {
            int_masiv = masiv[j];
            Hole nots = (Hole) harp.allnote.get(i + temp);
            Hole tabs = (Hole) harp.allnote.get(i + temp);
            j++;
            if (j == 7) {
                j = 0;
            }
            gammaview += isChecked ? nots.getNote() + " " : tabs.getTabs() + " ";
        }
        major.setText(gammaview);
        gammaview = "";
    }

    public void getGamma_minor(boolean isChecked) {
        int j = 0;
        int int_masiv = 0;
        int temp = check_temp(harp.position, harp2.position);
        final int[] masiv = {2, 1, 2, 2, 1, 2, 2};
        for (int i = 0; i < 37 - temp; i = i + int_masiv) {
            int_masiv = masiv[j];
            Hole nots = (Hole) harp.allnote.get(i + temp);
            Hole tabs = (Hole) harp.allnote.get(i + temp);
            j++;
            if (j == 7) {
                j = 0;
            }
            gammaview += isChecked ? nots.getNote() + " " : tabs.getTabs() + " ";
        }
        minor.setText(gammaview);
        gammaview = "";
    }

    public void getGamma_blues(boolean isChecked) {
        int j = 0;
        int int_masiv = 0;
        int temp = check_temp(harp.position, harp2.position);
        final int[] masiv = {3, 2, 1, 1, 3, 2};
        for (int i = 0; i < 37 - temp; i = i + int_masiv) {
            int_masiv = masiv[j];
            Hole nots = (Hole) harp.allnote.get(i + temp);
            Hole tabs = (Hole) harp.allnote.get(i + temp);
            j++;
            if (j == 6) {
                j = 0;
            }
            gammaview += isChecked ? nots.getNote() + " " : tabs.getTabs() + " ";
        }
        blues.setText(gammaview);
        gammaview = "";
    }

    public void get_minor_pentatonic(boolean isChecked) {
        int j = 0;
        int int_masiv = 0;
        int temp = check_temp(harp.position, harp2.position);
        final int[] masiv = {3, 2, 2, 3, 2};
        for (int i = 0; i < 37 - temp; i = i + int_masiv) {
            int_masiv = masiv[j];
            Hole nots = (Hole) harp.allnote.get(i + temp);
            Hole tabs = (Hole) harp.allnote.get(i + temp);
            j++;
            if (j == 5) {
                j = 0;
            }
            gammaview += isChecked ? nots.getNote() + " " : tabs.getTabs() + " ";
        }
        penta_minor.setText(gammaview);
        gammaview = "";
    }

    public void get_major_pentatonic(boolean isChecked) {
        int j = 0;
        int int_masiv = 0;
        int temp = check_temp(harp.position, harp2.position);
        final int[] masiv = {2, 2, 3, 2, 3};
        for (int i = 0; i < 37 - temp; i = i + int_masiv) {
            int_masiv = masiv[j];
            Hole nots = (Hole) harp.allnote.get(i + temp);
            Hole tabs = (Hole) harp.allnote.get(i + temp);
            j++;
            if (j == 5) {
                j = 0;
            }
            gammaview += isChecked ? nots.getNote() + " " : tabs.getTabs() + " ";
        }
        penta_major.setText(gammaview);
        gammaview = "";
    }

}
