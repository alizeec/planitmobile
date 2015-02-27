package com.example.alizeecamarasa.planit;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.events.EventAPI;
import com.example.alizeecamarasa.planit.events.EventService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Hamburger extends ActionBarActivity {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    // liste des noms des événements
    private String[] list;
    private Activity mActivity;
    // map contenant <Nom,id> des événements
    private Map<String,String> mapEvents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hamburger);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        mActivity = this;
        mapEvents = new LinkedHashMap<String, String>();
        addDrawerItems();
        setupDrawer();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void addDrawerItems() {
        // récupération de la liste des événements
        EventService service = EventAPI.getInstance();
        service.listEvents(((PlanItApplication) getApplicationContext()).USER_ID , new Callback<List<Event>>(){
            @Override
            public void success(List<Event> events, Response response){
                if(events!=null) {
                    list = new String[events.size()];
                    // remplissage de la liste
                    for (int i = 0; i < events.size(); i++) {
                        list[i] = events.get(i).getName();
                        mapEvents.put(events.get(i).getName(), events.get(i).getId());

                    }
                    mAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, list){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            TextView textView = (TextView) super.getView(position, convertView, parent);
                            textView.setTextColor(Hamburger.this.getResources().getColor(R.color.white));
                            return textView;
                        }
                    };
                    mDrawerList.setAdapter(mAdapter);
                }

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

        // clic sur un item
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idEvent= mapEvents.get(parent.getItemAtPosition(position));
                Intent intent= new Intent(getBaseContext(),EventActivity.class);
                intent.putExtra("event_id",idEvent);
                startActivity(intent);
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}