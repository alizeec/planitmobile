package com.example.alizeecamarasa.planit.events;

/**
 * Created by alizeecamarasa on 13/02/15.
 */
public class EventResponse {
    private String nbGuests;
    private String balance;
    private Event event;
    private float total_expenses;
    private float total_inflows;

    public String getNb_guest() {
       if(nbGuests == null)
           nbGuests = "0";
        return nbGuests;
    }

    public void setNb_guest(String nb_guest) {
        this.nbGuests = nb_guest;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getBalance() {
        if(balance == null) {
            balance ="0";
        }
        if(balance != null && balance.equals("Empty") )
            balance = "0";

        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public float getTotal_expenses() {
        return total_expenses;
    }

    public void setTotal_expenses(float total_expenses) {
        this.total_expenses = total_expenses;
    }

    public float getTotal_inflows() {
        return total_inflows;
    }

    public void setTotal_inflows(float total_inflows) {
        this.total_inflows = total_inflows;
    }
}
