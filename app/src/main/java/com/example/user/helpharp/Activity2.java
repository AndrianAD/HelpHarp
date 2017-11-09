package com.example.user.helpharp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import static com.example.user.helpharp.MainActivity.check_temp;

public class Activity2 extends Activity {
    TextView major, minor, blues, penta_minor, penta_major;
    static String gammaview = " ";
    Harp harp = new Harp();
    int out_spiner2 = 5;
    private static final Map<String, Integer> mapNotes = new HashMap<String, Integer>() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        harp.makeharp("Рихтеровская", 5);


        major = (TextView) findViewById(R.id.gamma_major);
        minor = (TextView) findViewById(R.id.gamma_minor);
        blues = (TextView) findViewById(R.id.gamma_blues);
        penta_minor = (TextView) findViewById(R.id.penta_minor);
        penta_major = (TextView) findViewById(R.id.penta_major);


        // -------Spiner1
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
// Настраиваем адаптер
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.harmonica_key, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Вызываем адаптер
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.harmonica_key);
                String mychoose = choose[selectedItemPosition];
                if (mapNotes.containsKey(mychoose)) ;
                int note = mapNotes.get(mychoose);
                harp.makeharp("Рихтеровская", note);
                getAllScales();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        // -------Spiner2
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
// Настраиваем адаптер
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Вызываем адаптер
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.harmonica_key);
                String mychoose = choose[selectedItemPosition];
                if (mapNotes.containsKey(mychoose)) ;
                out_spiner2 = mapNotes.get(mychoose);
                getAllScales();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


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

    //----------------------------------------------------------
    public void getGamma_major(boolean isChecked) {
        int j = 0, int_masiv;
        StringBuilder stringBuilder = new StringBuilder();
        int temp = check_temp(harp.position, out_spiner2);
        final int[] masiv = {2, 2, 1, 2, 2, 2, 1};

        for (int i = temp; i > 0; i = i - int_masiv) {
            int_masiv = masiv[j + 6];
            if (i - int_masiv < 0) {
                break;
            }
            Hole nots = (Hole) harp.allnote.get(i - int_masiv);
            j--;
            stringBuilder.insert(0, isChecked ? nots.getNote() + " " : nots.getTabs() + " ");
        }

        gammaview = String.valueOf(stringBuilder) + " T ";
        major.setText(gammaview);


        j = 0;
        for (int i = 0; i < 37 - temp; i = i + int_masiv) {
            int_masiv = masiv[j];
            Hole nots = (Hole) harp.allnote.get(i + temp);
            j++;
            if (j == 7) {
                j = 0;
            }
            gammaview += isChecked ? nots.getNote() + " " : nots.getTabs() + " ";
        }
        major.setText(gammaview);
        gammaview = "";
    }

    //----------------------------------------------------------
    public void getGamma_minor(boolean isChecked) {
        int j = 0, int_masiv;
        StringBuilder stringBuilder = new StringBuilder();
        int temp = check_temp(harp.position, out_spiner2);
        final int[] masiv = {2, 1, 2, 2, 1, 2, 2};

        for (int i = temp; i > 0; i = i - int_masiv) {
            int_masiv = masiv[j + 6];
            if (i - int_masiv < 0) {
                break;
            }
            Hole nots = (Hole) harp.allnote.get(i - int_masiv);
            j--;
            stringBuilder.insert(0, isChecked ? nots.getNote() + " " : nots.getTabs() + " ");
        }
        gammaview = String.valueOf(stringBuilder) + " T ";
        minor.setText(gammaview);


        j = 0;
        for (int i = 0; i < 37 - temp; i = i + int_masiv) {
            int_masiv = masiv[j];
            Hole nots = (Hole) harp.allnote.get(i + temp);
            j++;
            if (j == 7) {
                j = 0;
            }
            gammaview += isChecked ? nots.getNote() + " " : nots.getTabs() + " ";
        }
        minor.setText(gammaview);
        gammaview = "";
    }

    //----------------------------------------------------------
    public void getGamma_blues(boolean isChecked) {
        int j = 0, int_masiv;
        StringBuilder stringBuilder = new StringBuilder();
        int temp = check_temp(harp.position, out_spiner2);
        final int[] masiv = {3, 2, 1, 1, 3, 2};

        for (int i = temp; i > 0; i = i - int_masiv) {
            int_masiv = masiv[j + 5];
            if (i - int_masiv < 0) {
                break;
            }
            Hole nots = (Hole) harp.allnote.get(i - int_masiv);
            j--;
            stringBuilder.insert(0, isChecked ? nots.getNote() + " " : nots.getTabs() + " ");
        }
        gammaview = String.valueOf(stringBuilder) + " T ";
        blues.setText(gammaview);


        j = 0;
        for (int i = 0; i < 37 - temp; i = i + int_masiv) {
            int_masiv = masiv[j];
            Hole nots = (Hole) harp.allnote.get(i + temp);
            j++;
            if (j == 6) {
                j = 0;
            }
            gammaview += isChecked ? nots.getNote() + " " : nots.getTabs() + " ";
        }

        blues.setText(gammaview);
        gammaview = "";
    }

    //----------------------------------------------------------
    public void get_minor_pentatonic(boolean isChecked) {
        int j = 0, int_masiv;
        StringBuilder stringBuilder = new StringBuilder();
        int temp = check_temp(harp.position, out_spiner2);
        final int[] masiv = {3, 2, 2, 3, 2};

        for (int i = temp; i > 0; i = i - int_masiv) {
            int_masiv = masiv[j + 4];
            if (i - int_masiv < 0) {
                break;
            }
            Hole nots = (Hole) harp.allnote.get(i - int_masiv);
            j--;
            stringBuilder.insert(0, isChecked ? nots.getNote() + " " : nots.getTabs() + " ");
        }
        gammaview = String.valueOf(stringBuilder) + " T ";
        penta_minor.setText(gammaview);


        j = 0;
        for (int i = 0; i < 37 - temp; i = i + int_masiv) {
            int_masiv = masiv[j];
            Hole nots = (Hole) harp.allnote.get(i + temp);
            j++;
            if (j == 5) {
                j = 0;
            }
            gammaview += isChecked ? nots.getNote() + " " : nots.getTabs() + " ";
        }

        penta_minor.setText(gammaview);
        gammaview = "";
    }

    //----------------------------------------------------------
    public void get_major_pentatonic(boolean isChecked) {
        int j = 0, int_masiv;
        StringBuilder stringBuilder = new StringBuilder();
        int temp = check_temp(harp.position, out_spiner2);
        final int[] masiv = {2, 2, 3, 2, 3};

        for (int i = temp; i > 0; i = i - int_masiv) {
            int_masiv = masiv[j + 4];
            if (i - int_masiv < 0) {
                break;
            }
            Hole nots = (Hole) harp.allnote.get(i - int_masiv);
            j--;
            stringBuilder.insert(0, isChecked ? nots.getNote() + " " : nots.getTabs() + " ");
        }
        gammaview = String.valueOf(stringBuilder) + " T ";
        penta_major.setText(gammaview);


        j = 0;
        for (int i = 0; i < 37 - temp; i = i + int_masiv) {
            int_masiv = masiv[j];
            Hole nots = (Hole) harp.allnote.get(i + temp);
            j++;
            if (j == 5) {
                j = 0;
            }
            gammaview += isChecked ? nots.getNote() + " " : nots.getTabs() + " ";
        }

        penta_major.setText(gammaview);
        gammaview = "";
    }

    //----------------------------------------------------------
    private void getAllScales() {
        get_minor_pentatonic(false);
        get_major_pentatonic(false);
        getGamma_blues(false);
        getGamma_major(false);
        getGamma_minor(false);
    }

}
