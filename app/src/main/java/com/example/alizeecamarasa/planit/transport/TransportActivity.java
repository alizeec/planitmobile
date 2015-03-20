package com.example.alizeecamarasa.planit.transport;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.module.ModuleAPI;
import com.example.alizeecamarasa.planit.module.ModuleService;
import com.example.alizeecamarasa.planit.place.AddPlace;
import com.example.alizeecamarasa.planit.place.PlaceArrayAdapteur;
import com.example.alizeecamarasa.planit.place.Place;
import com.example.alizeecamarasa.planit.place.SeePlace;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;


/**
 * Created by alizeecamarasa on 09/03/15.
 */
public class TransportActivity extends ListActivity {

    Activity context;
    String id_module;
    TransportModuleService service;
    TransportModule mModule;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        context = this;
        id_module = getIntent().getStringExtra("module_id");
    }

    @Override
    protected void onStart() {
        super.onStart();
        service = TransportModuleAPI.getInstance();
        service.getModule(id_module,new Callback<TransportModule>() {
            @Override
            public void success(TransportModule transportModule, Response response) {
                // put the selected place on top of list
                mModule = transportModule;

                if(mModule.getTransportations().size()!=0){
                    findViewById(R.id.empty_list).setVisibility(View.INVISIBLE);
                }

                for (int i=0; i< transportModule.getTransportations().size(); i++){
                    if (transportModule.getTransportations().get(i).getState() == 1){
                        transportModule.getTransportations().add(0,transportModule.getTransportations().remove(i));
                    }
                }

                setListAdapter(new TransportArrayAdapteur(context,transportModule.getTransportations()));

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
                       //updateModuleTransport();
                        Toast.makeText(TransportActivity.this,"A venir",Toast.LENGTH_SHORT).show();
                    }
                });

                Button add_transport = (Button) findViewById(R.id.add_transport);
                add_transport.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent (context, AddTransport.class);
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
        Transport selectedTransport = (Transport)getListView().getItemAtPosition(position);
        Intent intent= new Intent(context,SeeTransport.class);
        intent.putExtra("transport",selectedTransport);
        startActivity(intent);

    }

    /* --------------------------------- UPDATE TRANSPORT MODULE -------------------------------------*/
    public void updateModuleTransport(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_logisticmodule);
        dialog.setTitle(R.string.name_module_transport);

        final EditText capacity_max = (EditText) dialog.findViewById(R.id.maxcapacity);
        final EditText price_max = (EditText) dialog.findViewById(R.id.maxPrice);
        final LinearLayout time_to_go = (LinearLayout) dialog.findViewById(R.id.timetogoLayout);
        time_to_go.setVisibility(View.GONE);

        // put currents data
        capacity_max.setText(String.valueOf(mModule.getMax_capacity_t()));
        price_max.setText(String.valueOf(mModule.getMax_price_t()));

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
                    moduleJson.put("max_capacity_t",Float.parseFloat(capacity_max.getText().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    moduleJson.put("max_price_t",Float.parseFloat(price_max.getText().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    json.put("transportationmodule_form",moduleJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println(json);
                TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());

                service.updateTransportModule(id_module, in, new Callback<Response>() {
                    @Override
                    public void success(Response s, Response response) {
                        Toast.makeText(TransportActivity.this, "Le module a bien été modifié!", Toast.LENGTH_SHORT).show();
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
