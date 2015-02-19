package com.example.alizeecamarasa.planit.module;

import com.example.alizeecamarasa.planit.events.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by alizeecamarasa on 26/01/15.
 */
public class Module {
    protected int id;
    protected String name;
    protected String slug;
    protected int inttype;
    //private Event event;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInt_type() {
        return inttype;
    }

    public void setInt_type(int int_type) {
        this.inttype = int_type;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String toString() {
        return name;
    }

/*    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }*/
}
