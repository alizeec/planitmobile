package com.example.alizeecamarasa.planit.events;

import java.text.SimpleDateFormat;
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
    private String image;

    public Event(String id, String name,String slug, String description,Date begin_date, Date end_date, String image) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.begin_date = begin_date;
        this.end_date = end_date;
        this.image = image;
    }

    public Event(int id, String name,String slug, String description,Date begin_date, Date end_date, String image) {
        this(Integer.toString(id), name,slug, description,begin_date,end_date,image);
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimeDiff(){
        Date cDate = new Date();
        String dateDiff;
        Long timeDiff = getBeginDate().getTime() - cDate.getTime();
        int day = (int) TimeUnit.MILLISECONDS.toDays(timeDiff);

        if(day >= 0){
            dateDiff ="J - "+day;
        }
        else {
            dateDiff="J + "+(-1)*day;
        }

        return dateDiff;
    }
}
