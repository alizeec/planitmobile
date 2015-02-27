package com.example.alizeecamarasa.planit.budget;

import com.example.alizeecamarasa.planit.budget.Item.ItemBudget;
import com.example.alizeecamarasa.planit.budget.TypeBudget.TypeBudget;
import com.example.alizeecamarasa.planit.module.Module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alizeecamarasa on 18/02/15.
 */
public class BudgetModule extends Module implements Serializable {

    private int max_budget;
    private int base;
    private List<TypeBudget> typesexpense;
    private List<ItemBudget> inflows;

    public int getMax_budget() {
        return max_budget;
    }

    public void setMax_budget(int max_budget) {
        this.max_budget = max_budget;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public List<TypeBudget> getTypeBudgetList() {
        return typesexpense;
    }

    public void setTypeBudgetList(List<TypeBudget> typeBudgetList) {
        this.typesexpense = typeBudgetList;
    }

    public List<ItemBudget> getInflows() {
        return inflows;
    }

    public void setInflows(List<ItemBudget> inflows) {
        this.inflows = inflows;
    }
}
