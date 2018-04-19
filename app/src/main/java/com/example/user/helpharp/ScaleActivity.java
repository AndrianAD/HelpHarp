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
import android.widget.GridLayout;
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
    private CheckBox[] arrayOfCheckBox;
    private int[] positionsGrid;
    private boolean flagMajor = false;
    private boolean flagMinor = false;
    private boolean flagBlues = false;
    private boolean flagPentaMajor = false;
    private boolean flagPentaMinor = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scale_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        harp.makeharp("Рихтеровская", out_spiner1);
        final GridLayout gridLayout = (GridLayout) findViewById(R.id.grid_layout);
        major = (TextView) findViewById(R.id.gamma_major);
        minor = (TextView) findViewById(R.id.gamma_minor);
        blues = (TextView) findViewById(R.id.gamma_blues);
        penta_minor = (TextView) findViewById(R.id.penta_minor);
        penta_major = (TextView) findViewById(R.id.penta_major);
        switchCheckBox_major = (CheckBox) findViewById(R.id.checkBox__major);
        switchCheckBox_blues = (CheckBox) findViewById(R.id.checkBox_blues);
        switchCheckBox_penta_major = (CheckBox) findViewById(R.id.checkBox_penta_major);
        switchCheckBox_penta_minor = (CheckBox) findViewById(R.id.checkBox_penta_minor);
        switchCheckBox_minor = (CheckBox) findViewById(R.id.checkBox_minor);
        switchCheckBox_major.setChecked(true);
        arrayOfCheckBox = new CheckBox[]{switchCheckBox_minor, switchCheckBox_blues, switchCheckBox_penta_minor, switchCheckBox_major, switchCheckBox_penta_major};
        final GridPositions gridPositions = new GridPositions();
        positionsGrid = gridPositions.positions_id_Rihter;
        show_harmonica(positionsGrid);


        // -------Spiner1
        final Spinner spinner_harmonica = (Spinner) findViewById(R.id.spinner1);
// Настраиваем адаптер`
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.harmonica_key, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Вызываем адаптер
        spinner_harmonica.setAdapter(adapter);
        spinner_harmonica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.harmonica_key);
                String mychoose = choose[selectedItemPosition];
                if (scaleNote.containsKey(mychoose)) ;
                out_spiner1 = scaleNote.get(mychoose);
                harp.makeharp(harp.stroi, out_spiner1);
                getAllScales();
                clear_view(gridLayout);
                show_harmonica(positionsGrid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // -------Spiner2
        final Spinner spinner_scale = (Spinner) findViewById(R.id.spinner2);
// Настраиваем адаптер
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Вызываем адаптер
        spinner_scale.setAdapter(adapter);
        spinner_scale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.harmonica_key);
                String mychoose = choose[selectedItemPosition];
                if (scaleNote.containsKey(mychoose)) ;
                out_spiner2 = scaleNote.get(mychoose);
                getAllScales();
                clear_view(gridLayout);
                show_harmonica(positionsGrid);
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
                    clear_view(gridLayout);
                    positionsGrid = gridPositions.positions_id_Rihter;
                    show_harmonica(positionsGrid);
                }
                if (choose[selectedItemPosition].equals("Падди")) {
                    harp.stroi = "Падди";
                    harp.makeharp("Падди", out_spiner1);
                    getAllScales();
                    clear_view(gridLayout);
                    positionsGrid = gridPositions.positions_id_Paddy;
                    show_harmonica(positionsGrid);
                }
                if (choose[selectedItemPosition].equals("Кантри")) {
                    harp.stroi = "Кантри";
                    harp.makeharp("Кантри", out_spiner1);
                    getAllScales();
                    clear_view(gridLayout);
                    positionsGrid = gridPositions.positions_id_Country;
                    show_harmonica(positionsGrid);
                }
                if (choose[selectedItemPosition].equals("Нат. Минор")) {
                    harp.stroi = "Нат. Минор";
                    harp.makeharp("Нат. Минор", out_spiner1);
                    getAllScales();
                    clear_view(gridLayout);
                    positionsGrid = gridPositions.positions_id_Natural_Minor;
                    show_harmonica(positionsGrid);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


//        // Чекбокс мажор
        switchCheckBox_major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (harp.allnote.isEmpty()) {
                    return;
                }
                deleselect_all_exeptOf();
                makeScale(!flagMajor, majorScale, major);
                flagMajor = !flagMajor;
                switchCheckBox_major.setChecked(true);
                clear_view(gridLayout);
                show_harmonica(positionsGrid);
            }
        });

// Чекбокс мінор
        switchCheckBox_minor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (harp.allnote.isEmpty()) {
                    return;
                }
                deleselect_all_exeptOf();
                makeScale(!flagMinor, minorScale, minor);
                flagMinor = !flagMinor;
                switchCheckBox_minor.setChecked(true);
                clear_view(gridLayout);
                show_harmonica(positionsGrid);

            }
        });

// Чекбокс блюз
        switchCheckBox_blues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (harp.allnote.isEmpty()) {
                    return;
                }
                deleselect_all_exeptOf();
                makeScale(!flagBlues, bluesScale, blues);
                flagBlues = !flagBlues;
                switchCheckBox_blues.setChecked(true);
                clear_view(gridLayout);
                show_harmonica(positionsGrid);

            }
        });

        // Чекбокс минорная пентатоника
        switchCheckBox_penta_minor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (harp.allnote.isEmpty()) {
                    return;
                }
                deleselect_all_exeptOf();
                makeScale(!flagPentaMinor, pentaminorScale, penta_minor);
                flagPentaMinor = !flagPentaMinor;
                switchCheckBox_penta_minor.setChecked(true);
                clear_view(gridLayout);
                show_harmonica(positionsGrid);


            }
        });

        // Чекбокс мажорная пентатоника
        switchCheckBox_penta_major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (harp.allnote.isEmpty()) {
                    return;
                }
                deleselect_all_exeptOf();
                makeScale(!flagPentaMajor, pentamajorScale, penta_major);
                flagPentaMajor = !flagPentaMajor;
                switchCheckBox_penta_major.setChecked(true);
                clear_view(gridLayout);
                show_harmonica(positionsGrid);

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
        if (resultView != null) {
            resultView.setText(sb3);
        }
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

    private void show_harmonica(int[] positionsGrid) {
        String all_Notes = harp.printlist(harp.allnote, false);
        String all_Tabs = harp.printlist(harp.allnote, true);
        String array_of_Notes[] = all_Notes.split(" ");
        String array_of_allTabs[] = all_Tabs.split(" ");
        TextView textView;
        Spinner spinner_scale = (Spinner) findViewById(R.id.spinner2);
        textView = (TextView) findViewById(R.id.b12);
        textView.setText(array_of_Notes[7]);
        for (int i = 0; i < positionsGrid.length; i++) {
            textView = (TextView) findViewById(positionsGrid[i]);
            textView.setText(array_of_Notes[i]);
        }
        int iterator = 0;
        String curent_scale[] = get_scale(if_checkBox_is_Cheked(arrayOfCheckBox));
        for (int x = 0; x < curent_scale.length; x++) {
            for (int y = iterator; y < positionsGrid.length; y++) {
                textView = (TextView) findViewById(positionsGrid[y]);
                String curentString = (String) textView.getText();
                if (curent_scale[x].equals(curentString)) {
                    textView.setTextColor(Color.parseColor("#0000cc"));
                    if (curentString.equals(spinner_scale.getSelectedItem().toString())) {
                        textView.setTextColor(Color.parseColor("#cc0000"));

                    }
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
        if (checkBox.getId() == R.id.checkBox__major) {
            scale = makeScale(true, majorScale, null);
        }
        if (checkBox.getId() == R.id.checkBox_minor) {
            scale = makeScale(true, minorScale, null);
        }
        if (checkBox.getId() == R.id.checkBox_blues) {
            scale = makeScale(true, bluesScale, null);
        }
        if (checkBox.getId() == R.id.checkBox_penta_major) {
            scale = makeScale(true, pentamajorScale, null);
        }
        if (checkBox.getId() == R.id.checkBox_penta_minor) {
            scale = makeScale(true, pentaminorScale, null);
        }


        array_scale = scale.split(" ");
        return array_scale;
    }

    private void clear_view(GridLayout layout) {
        final int childCount = layout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            TextView textView = (TextView) layout.getChildAt(i);
            textView.setTextColor(getResources().getColor(R.color.Default));
        }
        for (int i = 0; i < positionsGrid.length; i++) {
            TextView textView = (TextView) findViewById(positionsGrid[i]);
            textView.setText("");
        }


    }

    private void deleselect_all_exeptOf() {
        for (int i = 0; i < arrayOfCheckBox.length; i++) {
            if (arrayOfCheckBox[i].isChecked() == true) {
                arrayOfCheckBox[i].setChecked(false);
            }
        }
    }
}
