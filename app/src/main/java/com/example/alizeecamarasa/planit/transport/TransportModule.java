package com.example.alizeecamarasa.planit.transport;

import java.util.List;

/**
 * Created by alizeecamarasa on 08/03/15.
 */
public class TransportModule {
    private int max_capacity_t;
    private int max_price_t;
    private List<Transport> transportations;

    public int getMax_capacity_t() {
        return max_capacity_t;
    }

    public void setMax_capacity_t(int max_capacity_t) {
        this.max_capacity_t = max_capacity_t;
    }

    public int getMax_price_t() {
        return max_price_t;
    }

    public void setMax_price_t(int max_price_t) {
        this.max_price_t = max_price_t;
    }
}
