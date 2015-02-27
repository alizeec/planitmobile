package com.example.alizeecamarasa.planit.guest;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 02/02/15.
 */
public interface GuestModuleService {
    @GET("/modules/{id_module}")
    void getModule(@Path("id_module") String id_module, Callback<GuestModule> cb);

    @FormUrlEncoded
    @PUT("/guestsmodules/{id_module}/payable")
    void changePayable(@Path(value="id_module",encode=false) int id_module,@Field("payable") int payable, Callback<Response> cb);

    @GET("/guestsmodules/{module_id}/inscriptionlink")
    void getURL(@Path("module_id") String id_module, Callback<String> cb);

    @POST("/guestsmodules/{event_id}")
    void addGuestModule(@Path("event_id") String id_event, @Body TypedInput in,Callback<Response> cb);

    @POST("/guestsmodules/{module_id}/updates")
    void updateGuestModule(@Path("module_id") String module_id, @Body TypedInput in,Callback<Response> cb);
}
