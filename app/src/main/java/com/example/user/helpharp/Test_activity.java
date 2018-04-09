package com.example.user.helpharp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class Test_activity extends Activity {
    String fromIntent;
    private int[] allNotes_id = {R.id.b10, R.id.b40, R.id.b30, R.id.b00, R.id.b11, R.id.b51, R.id.b41, R.id.b31, R.id.b62, R.id.b52, R.id.b42, R.id.b32, R.id.b13,
            R.id.b43, R.id.b33, R.id.b03, R.id.b14, R.id.b34, R.id.b04, R.id.b15, R.id.b45, R.id.b35, R.id.b05, R.id.b36, R.id.b16,
            R.id.b46, R.id.b37, R.id.b07, R.id.b17, R.id.b38, R.id.b08, R.id.b18, R.id.b48, R.id.b39, R.id.b09, R.id.b19, R.id.b29, R.id.b49};
    private int[] major_id_view = {R.id.b20, R.id.b40, R.id.b30, R.id.b10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.harmonica_tabs);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        fromIntent = intent.getStringExtra("message");
        Toast toast = Toast.makeText(this, fromIntent,
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        String array_of_alltabs[] = fromIntent.split(" ");

        show_harmonica(array_of_alltabs);
    }


    private void show_harmonica(String[] array_of_alltabs) {
        TextView textView;
        textView = (TextView) findViewById(R.id.b12);
        textView.setText(array_of_alltabs[7]);
        for (int i = 0; i < allNotes_id.length; i++) {
            textView = (TextView) findViewById(allNotes_id[i]);
            textView.setText(array_of_alltabs[i]);
        }
    }


}
