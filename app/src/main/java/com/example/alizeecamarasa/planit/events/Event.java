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

    private String id;
    private String title;
    private String slug;
    private String description;
    private Date begin_date;
    private Date end_date;
    private long counter_day;
    private List<Module> modules;

    public Event(String id, String title,String slug, String description,Date begin_date, Date end_date) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.begin_date = begin_date;
        this.end_date = end_date;
    }

    public Event(int id, String title,String slug, String description,Date begin_date, Date end_date) {
        this(Integer.toString(id), title,slug, description,begin_date,end_date);
    }

    public Event(){

    }

    public static Event fromJson (JSONObject obj) throws JSONException{

        Event event = new Event();

        event.setId(obj.optString("id"));
        event.setTitle(obj.optString("name"));
        event.setSlug(obj.optString("slug"));
        event.setDescription(obj.optString("description"));

        JSONArray array = obj.getJSONArray("modules");
        List<Module> modules = new ArrayList<Module>(array.length());
        for(int i = 0 ; i < array.length(); i++){
            JSONObject object=array.getJSONObject(i);
            modules.add(Module.fromJson(object));
        }
        event.setModules(modules);

        String begin_date =obj.optString("begin_date");
        String end_date =obj.optString("end_date");

        if(begin_date!=null) {
            try {
                event.setBeginDate(ISO_FORMAT.parse(begin_date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(end_date!=null) {
            try {
                event.setEndDate(ISO_FORMAT.parse(end_date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Date cDate = new Date();
        Long timeDiff = event.getBeginDate().getTime() - cDate.getTime();
        int day = (int) TimeUnit.MILLISECONDS.toDays(timeDiff);
        event.setCounterDay(day);
        return event;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return title;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
