package com.example.alizeecamarasa.planit.place;

import com.example.alizeecamarasa.planit.guest.GuestModule;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 01/03/15.
 */
public interface PlaceModuleService {

    @GET("/modules/{id_module}")
    void getModule(@Path("id_module") String id_module, Callback<PlaceModule> cb);

    @FormUrlEncoded
    @PUT("/places/{place_id}/chose")
    void chosePlace(@Path(value="place_id",encode=false) int place_id ,@Field("check") int chosen, Callback<Response> cb);

    @DELETE("/places/{place_id}")
    void deletePlace(@Path("place_id") int place_id,Callback<Response> cb);

    @PUT("/places/{id_place}")
    void modifyPlace(@Path("id_place") int id_place,@Body TypedInput data, Callback<Response> cb);
}
