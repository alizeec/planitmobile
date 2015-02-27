package com.example.alizeecamarasa.planit.guest.TypeGuest;

import retrofit.RestAdapter;

/**
 * Created by alizeecamarasa on 19/02/15.
 */
public class TypeGuestAPI {

    private static TypeGuestService singleton;

    public static TypeGuestService getInstance() {
        if(singleton == null) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://planit.marion-lecorre.com/api")
                    .build();

            singleton = restAdapter.create(TypeGuestService.class);
        }
        return singleton;

    }
}
