package com.example.alizeecamarasa.planit.budget.TypeBudget;

import com.example.alizeecamarasa.planit.budget.Item.Item;

import java.util.List;

/**
 * Created by alizeecamarasa on 18/02/15.
 */
public class TypeBudget {
    private int id;
    private String name;
    private List<Item> expenses;

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

    public List<Item> getItems() {
        return expenses;
    }

    public void setItems(List<Item> expenses) {
        this.expenses = expenses;
    }
}
