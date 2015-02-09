package com.example.alizeecamarasa.planit.guest.Guest;

import com.example.alizeecamarasa.planit.guest.GuestModule;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by alizeecamarasa on 09/02/15.
 */
public interface GuestService {
    @DELETE("/guests/{id_guest}")
    void deleteGuest(@Path("id_guest") String id_module, Callback<Guest> cb);
}
