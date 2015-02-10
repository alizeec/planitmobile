package com.example.alizeecamarasa.planit.guest;

import android.os.Bundle;

import android.widget.CheckBox;
import android.widget.ExpandableListView;


import com.example.alizeecamarasa.planit.R;

import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.LinkedHashMap;


import com.example.alizeecamarasa.planit.guest.Guest.Guest;
import com.example.alizeecamarasa.planit.guest.TypeGuest.TypeGuest;


import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GuestActivity extends Activity {

    List<TypeGuest> groupList;
    List<Guest> childList;
    Map<TypeGuest, List<Guest>> guestCollection;
    ExpandableListView expListView;
    Activity context;
    GuestModuleService service;
    GuestModule mModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        context = this;
        service = GuestModuleAPI.getInstance();
        createGroupList();

    }


    private void createGroupList() {

        // appel au service, retourne le module
        service.getModule("6", new Callback<GuestModule>() {
            @Override
            public void success(GuestModule module, Response response) {
                if (module != null) {
                    mModule = module;
                    groupList = mModule.getType_guest();
                    createCollection();

                    expListView = (ExpandableListView) findViewById(R.id.laptop_list);
                    expListView.setGroupIndicator (null);

                    final TypeGuestArrayAdapter expListAdapter = new TypeGuestArrayAdapter(context, groupList, guestCollection, module);
                    expListView.setAdapter(expListAdapter);
                    // si l'événement est payant, on coche la checkbox
                    if(module.isPayable()==true){
                        CheckBox paying = (CheckBox)findViewById(R.id.checkbox_paying);
                        paying.setChecked(true);
                    }

                }

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

    }




   private void createCollection() {
        // preparing laptops collection(child)
        guestCollection = new LinkedHashMap<TypeGuest, List<Guest>>();

        for (TypeGuest type : groupList) {
                loadChild(type.getGuests());
            guestCollection.put(type, childList);
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

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_paying:
                if (checked) {
                    service.changePayable(mModule.getId(), 1, new Callback<JSONObject>() {
                        @Override
                        public void success(JSONObject module, Response response) {
                            System.out.println("success, result: " + module);
                            Toast.makeText(context, "L'événement est payant!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                        }
                    });
                }

                else {
                    service.changePayable(mModule.getId(), 0, new Callback<JSONObject>() {
                        @Override
                        public void success(JSONObject module, Response response) {
                            System.out.println("success, result: " + module);
                            Toast.makeText(context, "L'événement est gratuit!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                        }
                    });
                }
                break;

        }
    }
}
