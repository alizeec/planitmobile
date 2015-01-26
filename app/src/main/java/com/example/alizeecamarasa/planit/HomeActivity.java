package com.example.alizeecamarasa.planit;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by Yoann on 03/10/2014.
 */
public class HomeActivity extends ActionBarActivity {
    TextView  titleActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// load the view
        setContentView(R.layout.activity_home);
        ActionBar actionbar=getActionBar();
        actionbar.setCustomView(R.layout.custom_bar);
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM);
        actionbar.setDisplayHomeAsUpEnabled(false); // Remove '<' next to home icon.
        titleActionBar = (TextView)actionbar.getCustomView().findViewById(R.id.titleActionBar);
        titleActionBar.setText("Événements");

    }
}