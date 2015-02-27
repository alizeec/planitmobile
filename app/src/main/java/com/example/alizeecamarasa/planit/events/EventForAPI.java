package com.example.alizeecamarasa.planit.events;

import retrofit.mime.TypedFile;

/**
 * Created by alizeecamarasa on 23/02/15.
 */
public class EventForAPI {
    private String name;
    private String description;
    private String begin_date;
    private String end_date;
    private TypedFile image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public TypedFile getImage() {
        return image;
    }

    public void setImage(TypedFile image) {
        this.image = image;
    }
}
