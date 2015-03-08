package com.example.alizeecamarasa.planit.transport;

import com.example.alizeecamarasa.planit.place.PlaceModuleService;

import retrofit.RestAdapter;

/**
 * Created by alizeecamarasa on 09/03/15.
 */
public class TransportModuleAPI {
    private static TransportModuleService singleton;

    public static TransportModuleService getInstance() {
        if(singleton == null) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://planit.marion-lecorre.com/api")
                    .build();

            singleton = restAdapter.create(TransportModuleService.class);
        }
        return singleton;

    }
}
