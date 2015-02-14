package com.example.alizeecamarasa.planit;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by Yoann on 03/10/2014.
 */
public class HomeActivity extends Hamburger {
    TextView  titleActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// load the view
       /*
        titleActionBar = (TextView)actionbar.getCustomView().findViewById(R.id.titleActionBar);
        titleActionBar.setText("Événements");*/

        if ( savedInstanceState == null ) {

            //create main fragment : homepage of an event
            Fragment fragment = new HomeFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame,fragment).commit();
        }

    }
}