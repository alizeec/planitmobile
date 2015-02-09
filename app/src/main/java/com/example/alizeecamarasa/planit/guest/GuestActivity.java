package com.example.alizeecamarasa.planit.guest;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.alizeecamarasa.planit.EventActivity;
import com.example.alizeecamarasa.planit.EventFragment;
import com.example.alizeecamarasa.planit.R;

import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.LinkedHashMap;


import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.events.EventAPI;
import com.example.alizeecamarasa.planit.events.EventService;
import com.example.alizeecamarasa.planit.guest.Guest.Guest;
import com.example.alizeecamarasa.planit.guest.TypeGuest.TypeGuest;
import com.example.alizeecamarasa.planit.guest.GuestModuleAPI;
import com.example.alizeecamarasa.planit.guest.GuestModuleService;


import com.example.alizeecamarasa.planit.guest.TypeGuestArrayAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GuestActivity extends Activity {

    List<TypeGuest> groupList;
    List<Guest> childList;
    Map<TypeGuest, List<Guest>> laptopCollection;
    ExpandableListView expListView;
    Activity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        context = this;
        createGroupList();

    }


    private void createGroupList() {
        GuestModuleService service = GuestModuleAPI.getInstance();
        service.getModule("4", new Callback<GuestModule>() {
            @Override
            public void success(GuestModule module, Response response) {
                if (module != null) {
                    groupList = module.getType_guest();
                    createCollection();

                    expListView = (ExpandableListView) findViewById(R.id.laptop_list);
                    expListView.setGroupIndicator (null);


                    final TypeGuestArrayAdapter expListAdapter = new TypeGuestArrayAdapter(context, groupList, laptopCollection);
                    expListView.setAdapter(expListAdapter);


                    //setGroupIndicatorToRight();

                    expListView.setOnChildClickListener(new OnChildClickListener() {

                        public boolean onChildClick(ExpandableListView parent, View v,
                                                    int groupPosition, int childPosition, long id) {
                            final String selected = (String) expListAdapter.getChild(
                                    groupPosition, childPosition);
                            Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                                    .show();

                            return true;
                        }
                    });
                }

            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("erreur");
                error.printStackTrace();
            }
        });

    }



   private void createCollection() {
        // preparing laptops collection(child)
        laptopCollection = new LinkedHashMap<TypeGuest, List<Guest>>();

        for (TypeGuest type : groupList) {
                loadChild(type.getGuests());
            laptopCollection.put(type, childList);
        }
    }

    private void loadChild(List<Guest> laptopModels) {
        childList = new ArrayList<Guest>();
        for (Guest model : laptopModels)
            childList.add(model);
    }

    private void setGroupIndicatorToRight() {
        //* Get the screen width *//*
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_guest, menu);
        return true;
    }*/
}
