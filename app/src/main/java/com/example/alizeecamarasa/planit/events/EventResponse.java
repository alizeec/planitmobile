package com.example.alizeecamarasa.planit.events;

/**
 * Created by alizeecamarasa on 13/02/15.
 */
public class EventResponse {
    private String nb_guest;
    private String balance;
    private Event event;

    public String getNb_guest() {
       if(nb_guest == null)
           nb_guest = "0";
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

    public String getBalance() {
        if(balance.equals("Empty") || balance == null)
            balance = "0";
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
