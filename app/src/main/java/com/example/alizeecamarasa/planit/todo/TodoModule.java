package com.example.alizeecamarasa.planit.todo;

import com.example.alizeecamarasa.planit.module.Module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alizeecamarasa on 23/02/15.
 */
public class TodoModule extends Module implements Serializable {
    private List<CategoryTask> lists;

    public List<CategoryTask> getListCategories() {
        return lists;
    }

    public void setListCategories(List<CategoryTask> lists) {
        this.lists = lists;
    }
}
