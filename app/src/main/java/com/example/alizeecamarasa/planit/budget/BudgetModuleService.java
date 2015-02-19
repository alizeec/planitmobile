package com.example.alizeecamarasa.planit.budget;

import com.example.alizeecamarasa.planit.guest.GuestModule;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by alizeecamarasa on 18/02/15.
 */
public interface BudgetModuleService {

    @GET("/modules/{id_module}")
    void getModule(@Path("id_module") String id_module, Callback<BudgetModule> cb);

}
