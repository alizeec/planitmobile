package com.example.alizeecamarasa.planit.guest.TypeGuest;

import com.example.alizeecamarasa.planit.guest.Guest.Guest;
import com.example.alizeecamarasa.planit.module.Module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alizeecamarasa on 06/02/15.
 */
public class TypeGuest implements Serializable {
    private String id;
    private String label;
    private String message;
    private long price;
    private Module module;
    private List<Guest> guests;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public String toString(){
        return this.label;
    }

}
