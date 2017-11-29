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

import static com.example.user.helpharp.MainActivity.check_temp;

public class Activity2 extends Activity {
    TextView major, minor, blues, penta_minor, penta_major;
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
    final private int[] majorScale = {2, 2, 1, 2, 2, 2, 1};
    final private int[] minorScale = {2, 1, 2, 2, 1, 2, 2};
    final private int[] bluesScale = {3, 2, 1, 1, 3, 2};
    final private int[] pentamajorScale = {2, 2, 3, 2, 3};
    final private int[] pentaminorScale = {3, 2, 2, 3, 2};


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
                makeScale(isChecked, majorScale, major);
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
                makeScale(isChecked, minorScale, minor);
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
                makeScale(isChecked, bluesScale, blues);
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
                makeScale(isChecked, pentaminorScale, penta_minor);
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
                makeScale(isChecked, pentamajorScale, penta_major);
            }
        });
    }

    //----------------------------------------------------------
    public void makeScale(boolean isChecked, int masiv[], TextView resultView) {
        int j = 0, int_masiv;
        String firstString, secondString = "";
        StringBuilder stringBuilder = new StringBuilder();
        int temp = check_temp(harp.position, out_spiner2);
        for (int i = temp; i > 0; i = i - int_masiv) {
            int_masiv = masiv[j + masiv.length - 1];
            if (i - int_masiv < 0) {
                break;
            }
            Hole nots = (Hole) harp.allnote.get(i - int_masiv);
            j--;
            stringBuilder.insert(0, isChecked ? nots.getNote() + " " : nots.getTabs() + " ");
        }
        firstString = String.valueOf(stringBuilder);

        Hole nots = (Hole) harp.allnote.get(temp);
        String firsTonica = isChecked ? nots.getNote() + " " : nots.getTabs();
        nots = (Hole) harp.allnote.get(temp + 12);
        String secondTonica = isChecked ? nots.getNote() + " " : new String(" " + nots.getTabs() + " ");
        nots = (Hole) harp.allnote.get(temp + 24);
        String thirdTonica = isChecked ? nots.getNote() + " " : new String(" " + nots.getTabs() + " ");
        int lenght = firsTonica.length();
        int lenght2 = secondTonica.length();
        int lenght3 = thirdTonica.length();

        j = 0;
        for (int i = 0; i < 37 - temp; i = i + int_masiv) {
            int_masiv = masiv[j];
            nots = (Hole) harp.allnote.get(i + temp);
            j++;
            if (j == masiv.length) {
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
    }

    //----------------------------------------------------------
    private void getAllScales() {
        makeScale(false, majorScale, major);
        makeScale(false, minorScale, minor);
        makeScale(false, bluesScale, blues);
        makeScale(false, pentaminorScale, penta_minor);
        makeScale(false, pentamajorScale, penta_major);
    }

}
