package com.example.alizeecamarasa.planit.guest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by alizeecamarasa on 02/02/15.
 */
public class GuestModuleAPI {
    private static GuestModuleService singleton;

    public static GuestModuleService getInstance() {
        if(singleton == null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://planit.marion-lecorre.com/api")
                    .setConverter(new GsonConverter(gson))
                    .build();

            singleton = restAdapter.create(GuestModuleService.class);
        }
        return singleton;

    }
}
