package com.example.alizeecamarasa.planit.guest;

import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.guest.GuestModule;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by alizeecamarasa on 02/02/15.
 */
public interface GuestModuleService {
    @GET("/modules/{id_module}")
    void getModule(@Path("id_module") String id_module, Callback<GuestModule> cb);


}
