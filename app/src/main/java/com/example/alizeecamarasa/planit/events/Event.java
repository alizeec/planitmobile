package com.example.alizeecamarasa.planit.events;

import android.media.Image;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.alizeecamarasa.planit.module.Module;
/**
 * Created by alizeecamarasa on 30/12/14.
 */
public class Event {
    private static final SimpleDateFormat ISO_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    protected String id;
    protected String name;
    protected String slug;
    protected String description;
    protected Date begin_date;
    protected Date end_date;
    protected long counter_day;
    protected List<Module> modules;

    public Event(String id, String name,String slug, String description,Date begin_date, Date end_date) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.begin_date = begin_date;
        this.end_date = end_date;
    }

    public Event(int id, String name,String slug, String description,Date begin_date, Date end_date) {
        this(Integer.toString(id), name,slug, description,begin_date,end_date);
    }

    public Event(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }




    public Date getBeginDate() {
        return begin_date;
    }

    public void setBeginDate(Date begin_date) {
        this.begin_date = begin_date;
    }

    public Date getEndDate() {
        return end_date;
    }

    public void setEndDate(Date end_date) {
        this.end_date = end_date;
    }

    public long getCounterDay() {
        return counter_day;
    }

    public void setCounterDay(long counter_day) {
        this.counter_day = counter_day;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
