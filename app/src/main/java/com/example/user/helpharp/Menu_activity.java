package com.example.user.helpharp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu_activity extends Activity {
    private Button scale, tabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        scale = (Button) findViewById(R.id.scale);
        tabs = (Button) findViewById(R.id.tab);

        scale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Menu_activity.this, ScaleActivity.class);
                startActivity(myIntent);
            }
        });

        tabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Menu_activity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

    }


}
