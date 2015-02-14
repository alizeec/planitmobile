package com.example.alizeecamarasa.planit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;



import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.events.EventAPI;
import com.example.alizeecamarasa.planit.events.EventService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Yoann on 05/10/2014.
 */
public class EventActivity extends Hamburger {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load the view, désérialise la vue
        //setContentView(R.layout.activity_event);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);



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
