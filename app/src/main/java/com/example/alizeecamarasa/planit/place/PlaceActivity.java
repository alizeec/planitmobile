package com.example.alizeecamarasa.planit.place;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.guest.GuestActivity;
import com.example.alizeecamarasa.planit.module.Module;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.widget.AdapterView.OnItemClickListener;


/**
 * Created by alizeecamarasa on 01/03/15.
 */
public class PlaceActivity extends ListActivity {
    Activity context;
    String id_module;
    PlaceModuleService service;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        context = this;
        id_module = getIntent().getStringExtra("module_id");
    }

    @Override
    protected void onStart() {
        super.onStart();
        service = PlaceModuleAPI.getInstance();
        service.getModule(id_module,new Callback<PlaceModule>() {
            @Override
            public void success(PlaceModule placeModule, Response response) {
                // put the selected place on top of list
                for (int i=0; i< placeModule.getPlaces().size(); i++){
                    if (placeModule.getPlaces().get(i).getState() == 1){
                        placeModule.getPlaces().add(0,placeModule.getPlaces().remove(i));
                    }
                }

                setListAdapter(new PlaceArrayAdapteur(context,placeModule.getPlaces()));
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Place selectedPlace = (Place)getListView().getItemAtPosition(position);
        Intent intent= new Intent(context,SeePlace.class);
        intent.putExtra("place",selectedPlace);
        startActivity(intent);

    }
}
