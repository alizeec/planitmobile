package com.example.alizeecamarasa.planit.place;

import com.example.alizeecamarasa.planit.guest.GuestModuleService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by alizeecamarasa on 01/03/15.
 */
public class PlaceModuleAPI {
    private static PlaceModuleService singleton;

    public static PlaceModuleService getInstance() {
        if(singleton == null) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://planit.marion-lecorre.com/api")
                    .build();

            singleton = restAdapter.create(PlaceModuleService.class);
        }
        return singleton;

    }
}
