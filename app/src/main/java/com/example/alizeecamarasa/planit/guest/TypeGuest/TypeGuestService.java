package com.example.alizeecamarasa.planit.guest.TypeGuest;


import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 19/02/15.
 */
public interface TypeGuestService {

    @POST("/typeguests/{id_module}")
    void addTypeGuest(@Path("id_module") int id_module,@Body TypedInput data, Callback<Response> cb);

    @PUT("/typeguests/{id_type}")
    void changeTypeGuest(@Path(value="id_type",encode=false) String id_type,@Body TypedInput datasend, Callback<Response> cb);

    @DELETE("/typeguests/{id_type}")
    void deleteTypeGuest(@Path("id_type") String id_type, Callback<Response> cb);



}
