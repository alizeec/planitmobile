package com.example.alizeecamarasa.planit.guest.Guest;

import com.example.alizeecamarasa.planit.guest.GuestModule;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 09/02/15.
 */
public interface GuestService {
    @DELETE("/guests/{id_guest}")
    void deleteGuest(@Path("id_guest") String id_module, Callback<Guest> cb);

    @PUT("/guests/{id_guest}")
    void modifyGuest(@Path("id_guest") String id_guest,@Body TypedInput data, Callback<JSONObject> cb);

    @POST("/guests/{id_guest}/mails")
    void sendInvitGuest(@Path("id_guest") String id_guest,Callback<JSONObject> cb);
}
