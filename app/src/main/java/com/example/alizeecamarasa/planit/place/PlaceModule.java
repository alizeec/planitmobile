package com.example.alizeecamarasa.planit.place;

import java.util.List;

/**
 * Created by alizeecamarasa on 01/03/15.
 */
public class PlaceModule {
    private int max_capacity_p;
    private int max_price_p;
    private int max_time_to_go;
    private List<Place> places;

    public int getMax_capacity_p() {
        return max_capacity_p;
    }

    public void setMax_capacity_p(int max_capacity_p) {
        this.max_capacity_p = max_capacity_p;
    }

    public int getMax_price_p() {
        return max_price_p;
    }

    public void setMax_price_p(int max_price_p) {
        this.max_price_p = max_price_p;
    }

    public int getMax_time_to_go() {
        return max_time_to_go;
    }

    public void setMax_time_to_go(int max_time_to_go) {
        this.max_time_to_go = max_time_to_go;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
