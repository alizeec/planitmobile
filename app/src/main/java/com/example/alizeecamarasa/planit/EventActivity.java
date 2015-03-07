package com.example.alizeecamarasa.planit;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;


public class EventActivity extends Hamburger {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ( savedInstanceState == null ) {
            String id = getIntent().getStringExtra("event_id");
            Bundle bundle = new Bundle();
            bundle.putString("event_id", id);

            //create main fragment : homepage of an event
            Fragment fragment = new EventFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame,fragment).commit();

        }
    }
}
