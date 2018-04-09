package com.example.user.helpharp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

public class Test_activity extends Activity {
    String all_tabs;
    private int[] allNotes_id = {R.id.b10, R.id.b40, R.id.b30, R.id.b10};
    private int[] major_id_view = {R.id.b20, R.id.b40, R.id.b30, R.id.b10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.harmonica_tabs);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        all_tabs = intent.getStringExtra("message");
        Toast toast = Toast.makeText(this, all_tabs,
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
//        String array_of_alltabs[] = all_tabs.split(" ");
//        for (int i=0; i< major_id_view.length;i++)
//        {
//            TextView textView= (TextView) findViewById(major_id_view[i]);
//            textView.setText(array_of_alltabs[i]);
//        }
    }


}
