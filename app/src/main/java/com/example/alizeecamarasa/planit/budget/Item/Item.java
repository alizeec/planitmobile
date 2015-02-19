package com.example.alizeecamarasa.planit.budget.Item;

import java.io.Serializable;

/**
 * Created by alizeecamarasa on 18/02/15.
 */
public class Item implements Serializable{

    private int id;
    private String name;
    private String unit;
    private float price;
    private float stock;
    private float quantity;
    private float consummate;
    private boolean bought;
    private float amount;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getConsummate() {
        return consummate;
    }

    public void setConsummate(float consummate) {
        this.consummate = consummate;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
