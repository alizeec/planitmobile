package com.example.alizeecamarasa.planit.guest.PaymentMeans;

import java.io.Serializable;

/**
 * Created by alizeecamarasa on 18/02/15.
 */
public class PaymentMean implements Serializable {
    private int id;
    private String label;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
