package com.example.alizeecamarasa.planit.guest;

import retrofit.RestAdapter;

/**
 * Created by alizeecamarasa on 02/02/15.
 */
public class GuestModuleAPI {
    private static GuestModuleService singleton;

    public static GuestModuleService getInstance() {
        if(singleton == null) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://planit.marion-lecorre.com/api")
                    .build();

            singleton = restAdapter.create(GuestModuleService.class);
        }
        return singleton;

    }
}
