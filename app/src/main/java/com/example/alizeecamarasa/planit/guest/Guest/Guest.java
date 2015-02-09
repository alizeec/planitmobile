package com.example.alizeecamarasa.planit.guest.Guest;

/**
 * Created by alizeecamarasa on 06/02/15.
 */
public class Guest {
    private String id;
    private String firstname;
    private String lastname;
    private String email;

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
}
