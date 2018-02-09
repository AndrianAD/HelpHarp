package com.example.user.helpharp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu_activity extends Activity {
    private Button activityScale, activityTabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        activityScale = (Button) findViewById(R.id.scale);
        activityTabs = (Button) findViewById(R.id.tab);

        activityScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Menu_activity.this, ScaleActivity.class);
                startActivity(myIntent);
            }
        });

        activityTabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Menu_activity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

    }


}
