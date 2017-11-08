package com.example.user.helpharp;
import java.util.ArrayList;

public class Hole {
    private String note;
    private String tabs;

    public Hole(Note note, Note tabs) {
        this.tabs = tabs.getTab();
        this.note = note.getNota();
    }
    static Note[] masiv_note;

    public static ArrayList make_list(String stroi, int position) {
        chek_stroi(stroi);
        ArrayList temp_noteList = new ArrayList();
        for (int i = position; i < 37 + position + 1; i++) {
            Hole hole = new Hole(masiv_note[i], masiv_note[i - position]);
            temp_noteList.add(hole);
        }
        return temp_noteList;
    }

    public static ArrayList make_all_list() {
        ArrayList temp_noteList = new ArrayList();
        chek_stroi("Рихтеровская");
        for (int i = 0; i < masiv_note.length; i++) {
            Hole hole = new Hole(masiv_note[i], masiv_note[i]);
            temp_noteList.add(hole);
        }

        return temp_noteList;

    }



    private static void chek_stroi(String stroi) {
        if (stroi == "Рихтеровская") {
            masiv_note = new Note[]{
                    new Note("G", "1"), new Note("Ab", "-1'"), new Note("A", "-1"), new Note("Bb", "1*"),
                    new Note("B", "2"), new Note("C", "-2''"), new Note("C#", "-2'"), new Note("D", "-2"),
                    new Note("Eb", "-3'''"), new Note("E", "-3''"), new Note("F", "-3'"), new Note("F#", "-3"),
                    new Note("G", "4"), new Note("Ab", "-4'"), new Note("A", "-4"), new Note("Bb", "4*"),
                    new Note("B", "5"), new Note("C", "-5"), new Note("C#", "5*"), new Note("D", "6"),
                    new Note("Eb", "-6'"), new Note("E", "-6"), new Note("F", "6*"), new Note("F#", "-7"),
                    new Note("G", "7"), new Note("Ab", "-7*"), new Note("A", "-8"), new Note("Bb", "8'"),
                    new Note("B", "8"), new Note("C", "-9"), new Note("C#", "9'"), new Note("D", "9"),
                    new Note("Eb", "-9*"), new Note("E", "-10"), new Note("F", "10''"), new Note("F#", "10'"),
                    new Note("G", "10"), new Note("Ab", "10*"), new Note("A", ""), new Note("Bb", ""),
                    new Note("B", ""), new Note("C", ""), new Note("C#", ""), new Note("D", ""), new Note("Eb", ""),
                    new Note("E", ""), new Note("F", ""), new Note("F#", ""), new Note("G", "")};
        }
        if (stroi == "Падди") {
            masiv_note = new Note[]{
                    new Note("G", "1"), new Note("Ab", "-1'"), new Note("A", "-1"), new Note("Bb", "1*"),
                    new Note("B", "2"), new Note("C", "-2''"), new Note("C#", "-2'"), new Note("D", "-2"),
                    new Note("Eb", "3*"), new Note("E", "3"), new Note("F", "-3'"), new Note("F#", "-3"),
                    new Note("G", "4"), new Note("Ab", "-4'"), new Note("A", "-4"), new Note("Bb", "4*"),
                    new Note("B", "5"), new Note("C", "-5"), new Note("C#", "5*"), new Note("D", "6"),
                    new Note("Eb", "-6'"), new Note("E", "-6"), new Note("F", "6*"), new Note("F#", "-7"),
                    new Note("G", "7"), new Note("Ab", "-7*"), new Note("A", "-8"), new Note("Bb", "8'"),
                    new Note("B", "8"), new Note("C", "-9"), new Note("C#", "9'"), new Note("D", "9"),
                    new Note("Eb", "-9*"), new Note("E", "-10"), new Note("F", "10''"), new Note("F#", "10'"),
                    new Note("G", "10"), new Note("Ab", "10*"), new Note("A", ""), new Note("Bb", ""),
                    new Note("B", ""), new Note("C", ""), new Note("C#", ""), new Note("D", ""), new Note("Eb", ""),
                    new Note("E", ""), new Note("F", ""), new Note("F#", ""), new Note("G", "")};
        }

        if (stroi == "Кантри") {
            masiv_note = new Note[]{
                    new Note("G", "1"), new Note("Ab", "-1'"), new Note("A", "-1"), new Note("Bb", "1*"),
                    new Note("B", "2"), new Note("C", "-2''"), new Note("C#", "-2'"), new Note("D", "-2"),
                    new Note("Eb", "-3'''"), new Note("E", "-3''"), new Note("F", "-3'"), new Note("F#", "-3"),
                    new Note("G", "4"), new Note("Ab", "-4'"), new Note("A", "-4"), new Note("Bb", "4*"),
                    new Note("B", "5"), new Note("C", "-5'"), new Note("C#", "-5"), new Note("D", "6"),
                    new Note("Eb", "-6'"), new Note("E", "-6"), new Note("F", "6*"), new Note("F#", "-7"),
                    new Note("G", "7"), new Note("Ab", "-7*"), new Note("A", "-8"), new Note("Bb", "8'"),
                    new Note("B", "8"), new Note("C", "-9"), new Note("C#", "9'"), new Note("D", "9"),
                    new Note("Eb", "-9*"), new Note("E", "-10"), new Note("F", "10''"), new Note("F#", "10'"),
                    new Note("G", "10"), new Note("Ab", "10*"), new Note("A", ""), new Note("Bb", ""),
                    new Note("B", ""), new Note("C", ""), new Note("C#", ""), new Note("D", ""), new Note("Eb", ""),
                    new Note("E", ""), new Note("F", ""), new Note("F#", ""), new Note("G", "")};
        }
    }



    public String getNote() {
        return note;
    }

    public String getTabs() {
        return tabs;
    }


}


