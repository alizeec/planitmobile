package com.example.alizeecamarasa.planit;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;


public class HomeActivity extends Hamburger {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ( savedInstanceState == null ) {
            //create main fragment : homepage of an event
            Fragment fragment = new HomeFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame,fragment).commit();
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.removeItem(R.id.action_change_event);
        menu.removeItem(R.id.action_delete_event);
        return true;
    }


}