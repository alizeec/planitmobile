package com.example.alizeecamarasa.planit.budget;

import com.example.alizeecamarasa.planit.guest.GuestModule;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 18/02/15.
 */
public interface BudgetModuleService {

    @GET("/modules/{id_module}")
    void getModule(@Path("id_module") String id_module, Callback<BudgetModule> cb);

    @POST("/inflows/{id_module}")
    void addInflow(@Path("id_module") String id_module, @Body TypedInput data, Callback<JSONObject> cb);

    @POST("/typeexpenses/{id_module}")
    void addTypeExpense(@Path("id_module") String id_module, @Body TypedInput data, Callback<JSONObject> cb);

    @POST("/expenses/{id_type}")
    void addExpense(@Path("id_type") int id_type, @Body TypedInput data, Callback<JSONObject> cb);

    @PUT("/inflows/{id_inflow}")
    void changeInflow(@Path(value="id_inflow",encode=false) int id_inflow,@Body TypedInput payable, Callback<JSONObject> cb);

    @PUT("/expenses/{id_expense}")
    void changeExpense(@Path(value="id_expense",encode=false) int id_expense,@Body TypedInput payable, Callback<JSONObject> cb);

    @DELETE("/inflows/{id_inflow}")
    void deleteInflow(@Path("id_inflow") int id_inflow, Callback<JSONObject> cb);

    @DELETE("/expenses/{id_expense}")
    void deleteExpense(@Path("id_expense") int id_expense, Callback<JSONObject> cb);
}
