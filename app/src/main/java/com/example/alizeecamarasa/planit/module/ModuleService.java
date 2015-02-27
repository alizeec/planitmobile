package com.example.alizeecamarasa.planit.module;

import com.example.alizeecamarasa.planit.budget.BudgetModule;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.Path;

/**
 * Created by alizeecamarasa on 23/02/15.
 */
public interface ModuleService {

    @DELETE("/modules/{module_id}")
    void deleteModule(@Path("module_id") String id_module, Callback<Response> cb);
}
