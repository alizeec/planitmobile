package com.example.alizeecamarasa.planit.transport;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.alizeecamarasa.planit.R;


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
        setContentView(R.layout.activity_place);
        context = this;
        id_module = getIntent().getStringExtra("module_id");
    }
}
