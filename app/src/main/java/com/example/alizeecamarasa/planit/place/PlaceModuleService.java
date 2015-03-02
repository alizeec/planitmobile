package com.example.alizeecamarasa.planit.place;

import com.example.alizeecamarasa.planit.guest.GuestModule;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by alizeecamarasa on 01/03/15.
 */
public interface PlaceModuleService {

    @GET("/modules/{id_module}")
    void getModule(@Path("id_module") String id_module, Callback<PlaceModule> cb);
}
