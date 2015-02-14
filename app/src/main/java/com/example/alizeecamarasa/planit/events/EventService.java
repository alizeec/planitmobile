package com.example.alizeecamarasa.planit.events;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by alizeecamarasa on 02/02/15.
 */
public interface EventService {

    @GET("/users/{id}/events")
    void listEvents(@Path("id") String id, Callback<List<Event>> cb);

    @GET("/events/{event_id}")
    void getEvent(@Path("event_id") String id, Callback<EventResponse> cb);
}
