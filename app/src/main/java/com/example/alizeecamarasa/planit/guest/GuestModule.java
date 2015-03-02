package com.example.alizeecamarasa.planit.guest;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.alizeecamarasa.planit.guest.TypeGuest.TypeGuest;
import com.example.alizeecamarasa.planit.module.Module;

import org.json.JSONException;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by alizeecamarasa on 02/02/15.
 */
public class GuestModule extends Module implements Serializable {
    private int maxguests;
    private boolean payable;
    private List<TypeGuest> typesguests;
    private boolean moduletype;

    public int getMax_guests() {
        return maxguests;
    }

    public void setMax_guests(int max_guests) {
        this.maxguests = max_guests;
    }

    public boolean isPayable() {
        return payable;
    }

    public void setPayable(boolean payable) {
        this.payable = payable;
    }

    public List<TypeGuest> getType_guest() {
        return typesguests;
    }

    public void setType_guest(List<TypeGuest> type_guest) {
        this.typesguests = type_guest;
    }

    public String getIdTypeGuestwithLabel(String label){
        try{
            for(TypeGuest type : typesguests){
                if( type.getLabel().equals(label)){
                    return type.getId();
                }
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return label;
    }

    public boolean isModuletype() {
        return moduletype;
    }

    public void setModuletype(boolean moduletype) {
        this.moduletype = moduletype;
    }
}
