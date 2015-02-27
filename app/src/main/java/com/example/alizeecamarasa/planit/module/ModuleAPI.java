package com.example.alizeecamarasa.planit.module;

import com.example.alizeecamarasa.planit.budget.BudgetModuleService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by alizeecamarasa on 24/02/15.
 */
public class ModuleAPI {
    private static ModuleService singleton;

    public static ModuleService getInstance() {
        if(singleton == null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://planit.marion-lecorre.com/api")
                    .setConverter(new GsonConverter(gson))
                    .build();

            singleton = restAdapter.create(ModuleService.class);
        }
        return singleton;

    }
}
