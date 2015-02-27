package com.example.alizeecamarasa.planit.events;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by alizeecamarasa on 30/12/14.
 */
public class EventAPI {
    private static EventService singleton;

    public static EventService getInstance() {
        if(singleton == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://planit.marion-lecorre.com/api")
                    .setConverter(new GsonConverter(gson))
                    .build();

            singleton = restAdapter.create(EventService.class);
        }
        return singleton;

    }
}
