package com.example.alizeecamarasa.planit.events;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 02/02/15.
 */
public interface EventService {

    @GET("/users/{id}/events")
    void listEvents(@Path("id") String id, Callback<List<Event>> cb);

    @GET("/events/{event_id}")
    void getEvent(@Path("event_id") String id, Callback<EventResponse> cb);

    @POST("/events/{id_user}")
    void addEvent(@Path("id_user") Integer id_user,@Body TypedInput data, Callback<JSONObject> cb);


}
