package com.example.alizeecamarasa.planit.place;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.guest.GuestActivity;
import com.example.alizeecamarasa.planit.module.Module;
import com.example.alizeecamarasa.planit.module.ModuleAPI;
import com.example.alizeecamarasa.planit.module.ModuleService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by alizeecamarasa on 01/03/15.
 */
public class PlaceActivity extends ListActivity {
    Activity context;
    String id_module;
    PlaceModuleService service;
    PlaceModule mModule;
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
                mModule = placeModule;
                for (int i=0; i< placeModule.getPlaces().size(); i++){
                    if (placeModule.getPlaces().get(i).getState() == 1){
                        placeModule.getPlaces().add(0,placeModule.getPlaces().remove(i));
                    }
                }

                setListAdapter(new PlaceArrayAdapteur(context,placeModule.getPlaces()));

                // DELETE MODULE
                final ModuleService moduleService = ModuleAPI.getInstance();
                ImageButton delete = (ImageButton) findViewById(R.id.delete_module);
                delete.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        moduleService.deleteModule(id_module, new Callback<Response>() {
                            @Override
                            public void success(Response budgetModule, Response response) {
                                finish();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                finish();
                                error.printStackTrace();
                            }
                        });

                    };
                });
                // UPDATE MODULE
                ImageButton update = (ImageButton) findViewById(R.id.modify_module);
                update.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        updateModulePlace();
                    }
                });

                Button add_place = (Button) findViewById(R.id.add_place);
                add_place.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent (context, AddPlace.class);
                        intent.putExtra("id",id_module);
                        context.startActivity(intent);
                    }
                });
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

    /* --------------------------------- UPDATE PLACE MODULE -------------------------------------*/
    public void updateModulePlace(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_placemodule);
        dialog.setTitle(R.string.name_module_place);

        final EditText capacity_max = (EditText) dialog.findViewById(R.id.maxcapacity);
        final EditText price_max = (EditText) dialog.findViewById(R.id.maxPrice);
        final EditText time_max = (EditText) dialog.findViewById(R.id.maxTimeToGo);

        // put currents data
        capacity_max.setText(String.valueOf(mModule.getMax_capacity_p()));
        price_max.setText(String.valueOf(mModule.getMax_price_p()));
        time_max.setText(String.valueOf(mModule.getMax_time_to_go()));

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button validate = (Button) dialog.findViewById(R.id.validatenewmodule);
        validate.setText("Modifier");
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject json = new JSONObject();
                JSONObject moduleJson = new JSONObject();
                try {
                    moduleJson.put("max_capacity_p",Float.parseFloat(capacity_max.getText().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    moduleJson.put("max_price_p",Float.parseFloat(price_max.getText().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    moduleJson.put("max_time_to_go",Float.parseFloat(time_max.getText().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    json.put("placemodule_form",moduleJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());

                service.updatePlaceModule(id_module, in, new Callback<Response>() {
                    @Override
                    public void success(Response s, Response response) {
                        Toast.makeText(PlaceActivity.this, "Le module a bien été modifié!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
