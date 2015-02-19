package com.example.alizeecamarasa.planit.guest.Guest;

import com.example.alizeecamarasa.planit.guest.PaymentMeans.PaymentMean;

import java.io.Serializable;

/**
 * Created by alizeecamarasa on 06/02/15.
 */
public class Guest implements Serializable{
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private int sent;
    private int confirmed;
    private int payed;
    private PaymentMean paymentmean;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return firstname;
    }

    public int getSent() {
        return sent;
    }

    public void setSent(int sent) {
        this.sent = sent;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getPayed() {
        return payed;
    }

    public void setPayed(int payed) {
        this.payed = payed;
    }

    public PaymentMean getPaymentmean() {
        return paymentmean;
    }

    public void setPaymentmean(PaymentMean paymentmean) {
        this.paymentmean = paymentmean;
    }
}
