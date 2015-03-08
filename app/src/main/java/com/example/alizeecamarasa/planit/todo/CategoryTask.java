package com.example.alizeecamarasa.planit.todo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alizeecamarasa on 26/02/15.
 */
public class CategoryTask implements Serializable {
    private int id;
    private String name;
    private List<Task> items;

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

    public List<Task> getItems() {
        return items;
    }

    public void setItems(List<Task> items) {
        this.items = items;
    }

    public String toString(){
        return name;
    }
}
