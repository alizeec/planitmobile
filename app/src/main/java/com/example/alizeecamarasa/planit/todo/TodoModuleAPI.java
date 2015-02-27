package com.example.alizeecamarasa.planit.todo;

import com.example.alizeecamarasa.planit.guest.GuestModuleService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by alizeecamarasa on 26/02/15.
 */
public class TodoModuleAPI {
    private static TodoModuleService singleton;

    public static TodoModuleService getInstance() {
        if(singleton == null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://planit.marion-lecorre.com/api")
                    .setConverter(new GsonConverter(gson))
                    .build();

            singleton = restAdapter.create(TodoModuleService.class);
        }
        return singleton;

    }
}
