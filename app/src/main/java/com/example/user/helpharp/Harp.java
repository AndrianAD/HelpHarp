package com.example.user.helpharp;

import android.widget.TextView;

import java.util.ArrayList;

import static com.example.user.helpharp.Hole.make_list;

public class Harp {

    String key_of_harp;
    String stroi = "";
    int position=5;
    public ArrayList<Hole> allnote = new ArrayList();

    public void makeharp(String stroi,int position,TextView textView){
        allnote=make_list(stroi,position);
        key_of_harp = ((Hole) allnote.get(0)).getNote();
        this.stroi=stroi;
        this.position=position;
        textView.setText(key_of_harp);

    }

    public void makeharp(String stroi, int position) {
        allnote = make_list(stroi, position);
        key_of_harp = ((Hole) allnote.get(0)).getNote();
        this.stroi = stroi;
        this.position = position;
    }


    public String printlist(ArrayList list, TextView textView){
        String temp="";
        Hole hole;
        for(int i = 0; i<list.size(); i++){
            hole= (Hole) list.get(i);
            temp+=hole.getTabs()+" ";
        }
        textView.setText(temp);
        return temp;
    }

    public String printlist(ArrayList list, boolean tabsOrNote) {
        String temp = "";
        Hole hole;
        for (int i = 0; i < list.size(); i++) {
            hole = (Hole) list.get(i);
            temp += tabsOrNote ? hole.getTabs() + " " : hole.getNote() + " ";
        }
        return temp;
    }

}
