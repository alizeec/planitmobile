package com.example.alizeecamarasa.planit.events;

import android.content.Context;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alizeecamarasa on 30/12/14.
 */
public class EventAPI {

    public static List<Event> getEvents (Context context) throws Exception {
        Pair<Integer, String> result=  SimpleRestApi.get("http://planit.marion-lecorre.com/api/users/1/events");
        if(result.first != 200){
            throw new IOException("job not found. Code="+result.first);
        }

        JSONArray array=new JSONArray(result.second);
        ArrayList<Event> events = new ArrayList<Event>(array.length());
        for(int i = 0 ; i < array.length(); i++){
            JSONObject object=array.getJSONObject(i);
            events.add(Event.fromJson(object));
        }

        return events;

    }

    public static Event getEvent (Context context, String eventId) throws Exception {
        Pair<Integer, String> result=  SimpleRestApi.get("http://planit.marion-lecorre.com/api/events/"+eventId);
        if(result.first != 200){
            throw new IOException("job not found. Code="+result.first);
        }
        JSONObject object=new JSONObject(result.second);
        return Event.fromJson(object);

    }

    private static final String API_BASE_URL = "http://jobboard-api.herokuapp.com/api/";

    public static boolean postApply (Context context, String jobId, String email, String message) throws IOException, JSONException {
        JSONObject object = new JSONObject();
        object.put("job",jobId);
        object.put("email", email);
        object.put("message", message);
        object.put("name", "Android");

        Pair<Integer, String> response = SimpleRestApi.post(API_BASE_URL + "applies", object.toString());

        return response.first == 200;
    }

}
