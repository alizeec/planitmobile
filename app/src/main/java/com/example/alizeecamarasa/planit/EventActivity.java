package com.example.alizeecamarasa.planit;


import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;



public class EventActivity extends Hamburger {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if save instance state bundle is not null (orientation change, ...) then the fragment is automatically reloaded
       if(savedInstanceState == null) {

            String id = getIntent().getStringExtra("event_id");
            Bundle bundle = new Bundle();
            bundle.putString("event_id", id);

            // Create fragment and add bundle
           EventFragment fragment = new EventFragment();
           fragment.setArguments(bundle);
           FragmentManager fragmentManager = getFragmentManager();
           FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           fragmentTransaction.replace(R.id.content_frame,fragment).commit();

       }
    }
}
