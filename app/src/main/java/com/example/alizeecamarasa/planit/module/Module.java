package com.example.alizeecamarasa.planit.module;

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
    private int id;
    private String name;
    private String slug;
    private int int_type;


    public static Module fromJson (JSONObject obj) throws JSONException {

        Module event = new Module();

        event.setId(Integer.parseInt(obj.optString("id")));
        event.setName(obj.optString("name"));
        event.setSlug(obj.optString("slug"));
        event.setInt_type(Integer.parseInt(obj.optString("int_type")));

        return event;
    }

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
        return int_type;
    }

    public void setInt_type(int int_type) {
        this.int_type = int_type;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
