package com.example.alizeecamarasa.planit.events;

import android.content.Context;
import android.provider.SyncStateContract;
import android.util.Pair;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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



    private static final String API_BASE_URL = "http://jobboard-api.herokuapp.com/api/";

    public static boolean postApply (Context context, String jobId, String email, String message) throws IOException, JSONException {
        JSONObject object = new JSONObject();
        object.put("job",jobId);
        object.put("email", email);
        object.put("message", message);
        object.put("name", "Android");

        Pair<Integer, String> response = SimpleRestApi.post(API_BASE_URL + "applies", object.toString());

        return response.first == 200;
    }

}
