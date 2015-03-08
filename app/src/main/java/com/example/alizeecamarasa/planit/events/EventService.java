package com.example.alizeecamarasa.planit.events;


import java.util.List;


import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedString;

/**
 * Created by alizeecamarasa on 02/02/15.
 */
public interface EventService {

    @GET("/users/{id}/events")
    void listEvents(@Path("id") String id, Callback<List<Event>> cb);

    @GET("/events/{event_id}")
    void getEvent(@Path("event_id") String id, Callback<EventResponse> cb);

    @DELETE("/events/{event_id}")
    void deleteEvent(@Path("event_id") String id, Callback<EventResponse> cb);

    @POST("/events/{event_id}/updates")
    void updateEvent(@Path("event_id") String event_id, @Body TypedInput in,Callback<Response> cb);

    @Multipart
    @POST("/events/{id_user}")
    void addEvent(@Path("id_user") String id_user,@Part("name") String name,@Part("description") String description,@Part("begin_date") String begin_date,@Part("end_date") String end_date,@Part("image") TypedFile image,Callback<Response> cb);
}
