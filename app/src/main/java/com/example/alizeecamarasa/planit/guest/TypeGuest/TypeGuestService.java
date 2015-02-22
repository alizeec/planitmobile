package com.example.alizeecamarasa.planit.guest.TypeGuest;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 19/02/15.
 */
public interface TypeGuestService {

    @POST("/typeguests/{id_module}")
    void addTypeGuest(@Path("id_module") String id_module,@Body TypedInput data, Callback<JSONObject> cb);
}
