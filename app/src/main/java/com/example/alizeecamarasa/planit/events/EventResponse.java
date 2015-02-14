package com.example.alizeecamarasa.planit.events;

/**
 * Created by alizeecamarasa on 13/02/15.
 */
public class EventResponse {
    private String nb_guest;
    private Event event;

    public String getNb_guest() {
        return nb_guest;
    }

    public void setNb_guest(String nb_guest) {
        this.nb_guest = nb_guest;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
