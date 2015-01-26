package com.example.alizeecamarasa.planit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Yoann on 05/10/2014.
 */
public class EventActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load the view, désérialise la vue
        setContentView(R.layout.activity_event);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        // if save instance state bundle is not null (orientation change, ...) then the fragment is automatically reloaded
        if(savedInstanceState == null) {

            //Get job id in intent extra
            String id = getIntent().getStringExtra("event_id");
            //add job id in the fragment bundle (a package)
            Bundle bundle = new Bundle();
            bundle.putString("event_id",id);

            // Create fragment and add bundle
            Fragment fragment = new EventFragment();
            fragment.setArguments(bundle);

            // Load fragment in the Activity
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_event_layout, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, HomeActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
