package com.example.alizeecamarasa.planit.guest.Guest;

import com.example.alizeecamarasa.planit.guest.GuestModuleService;

import retrofit.RestAdapter;

/**
 * Created by alizeecamarasa on 09/02/15.
 */
public class GuestAPI {
    private static GuestService singleton;

    public static GuestService getInstance() {
        if(singleton == null) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://planit.marion-lecorre.com/api")
                    .build();

            singleton = restAdapter.create(GuestService.class);
        }
        return singleton;

    }
}
