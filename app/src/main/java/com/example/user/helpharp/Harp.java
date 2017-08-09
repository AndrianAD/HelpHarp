package com.example.user.helpharp;
import android.widget.TextView;

import java.util.ArrayList;
import static com.example.user.helpharp.Hole.make_list;

public class Harp {

    String key_of_harp;
    String stroi="Рихтеровская";
    int position=5;
    public ArrayList allnote = new ArrayList();

    public void makeharp(String stroi,int position,TextView textView){
        allnote=make_list(stroi,position);
        Hole hole = (Hole) allnote.get(0);
        key_of_harp= hole.getNote();
        this.stroi=stroi;
        this.position=position;
        textView.setText(key_of_harp);

    }
    public String printlist(ArrayList list,TextView textView){
        String temp="";
            Hole hole;
            for(int i=0; i<list.size(); i++){
                hole= (Hole) list.get(i);
                temp+=hole.getTabs()+" ";
            }
        textView.setText(temp);
                return temp;

        }


}
