package com.example.alizeecamarasa.planit.transport;

import java.io.Serializable;

/**
 * Created by alizeecamarasa on 08/03/15.
 */
public class Transport implements Serializable{
    private int id;
    private String name;
    private String tel;
    private float price;
    private float capacity;
    private String website;
    private int state;
    private int oldstate;
    private String contract;

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOldstate() {
        return oldstate;
    }

    public void setOldstate(int oldstate) {
        this.oldstate = oldstate;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }
}
