package com.example.alizeecamarasa.planit.budget;

import com.example.alizeecamarasa.planit.budget.BudgetModule;

/**
 * Created by alizeecamarasa on 12/03/15.
 */
public class ResponseBudgetModule {
    private BudgetModule module;
    private float guestsInflow;

    public float getGuestsInflow() {
        return guestsInflow;
    }

    public void setGuestsInflow(float guestsInflow) {
        this.guestsInflow = guestsInflow;
    }

    public BudgetModule getModule() {
        return module;
    }

    public void setModule(BudgetModule module) {
        this.module = module;
    }
}
