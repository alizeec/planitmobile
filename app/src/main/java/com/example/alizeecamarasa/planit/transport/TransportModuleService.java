package com.example.alizeecamarasa.planit.transport;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 09/03/15.
 */
public interface TransportModuleService {

    @GET("/transportationmodules/{event_id}")
    void getModule(@Path("event_id") int event_id, Callback<TransportModule> cb);

    @POST("/transportationmodules/{module_id}/updates")
    void updateTransportModule(@Path("module_id") String module_id, @Body TypedInput in,Callback<Response> cb);

    @POST("/transportationmodules/{event_id}")
    void addTransportModule(@Path("event_id") String id_event,@Body TypedInput in, Callback<Response> cb);

    @PUT("/transportations/{id_transport}")
    void modifyTransport(@Path("id_transport") int id_transport,@Body TypedInput data, Callback<Response> cb);

    @FormUrlEncoded
    @PUT("/transportations/{id_transport}/chose")
    void choseTransport(@Path(value="id_transport",encode=false) int id_transport ,@Field("check") int chosen, Callback<Response> cb);

    @DELETE("/transportations/{id_transport}")
    void deleteTransport(@Path("id_transport") int id_transport,Callback<Response> cb);

}
