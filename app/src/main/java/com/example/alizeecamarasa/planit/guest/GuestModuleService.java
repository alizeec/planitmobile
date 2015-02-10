package com.example.alizeecamarasa.planit.guest;

import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.guest.GuestModule;

import org.json.JSONObject;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;

/**
 * Created by alizeecamarasa on 02/02/15.
 */
public interface GuestModuleService {
    @GET("/modules/{id_module}")
    void getModule(@Path("id_module") String id_module, Callback<GuestModule> cb);

    @FormUrlEncoded
    @PUT("/guestsmodules/{id_module}/payable")
    void changePayable(@Path(value="id_module",encode=false) int id_module,@Field("payable") int payable, Callback<JSONObject> cb);
}
