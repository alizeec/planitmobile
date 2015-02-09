package com.example.alizeecamarasa.planit.guest;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.alizeecamarasa.planit.guest.TypeGuest.TypeGuest;
import com.example.alizeecamarasa.planit.module.Module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alizeecamarasa on 02/02/15.
 */
public class GuestModule extends Module implements Serializable {
    private int max_guests;
    private boolean payable;
    private List<TypeGuest> type_guest;
    private int mData;

    public int getMax_guests() {
        return max_guests;
    }

    public void setMax_guests(int max_guests) {
        this.max_guests = max_guests;
    }

    public boolean isPayable() {
        return payable;
    }

    public void setPayable(boolean payable) {
        this.payable = payable;
    }

    public List<TypeGuest> getType_guest() {
        return type_guest;
    }

    public void setType_guest(List<TypeGuest> type_guest) {
        this.type_guest = type_guest;
    }

}
