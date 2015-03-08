package com.example.alizeecamarasa.planit.todo;

import retrofit.Callback;
import retrofit.client.Response;
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
 * Created by alizeecamarasa on 26/02/15.
 */
public interface TodoModuleService {

    @GET("/modules/{id_module}")
    void getModule(@Path("id_module") String id_module, Callback<TodoModule> cb);

    @POST("/todomodules/{event_id}")
    void addTodoModule(@Path("event_id") String id_event,Callback<Response> cb);

    @POST("/items/{list_id}")
    void addTask(@Path("list_id") int list_id,@Body TypedInput in, Callback<Response> cb);

    @DELETE("/items/{item_id}")
    void deleteTask(@Path("item_id") int item_id,Callback<Response> cb);

    @FormUrlEncoded
    @PUT("/items/{id_item}/checked")
    void changeChecked(@Path(value="id_item",encode=false) int id_item,@Field("checked") int checked, Callback<Response> cb);

    @POST("/tasklists/{module_id}")
    void addCategoryTask(@Path("module_id") int id_module,@Body TypedInput in, Callback<Response> cb);

    @PUT("/tasklists/{list_id}")
    void changeCategoryTask(@Path("list_id") int list_id, @Body TypedInput in, Callback<Response> cb);

    @DELETE("/tasklists/{list_id}")
    void deleteCategoryTask(@Path("list_id") int list_id,Callback<Response> cb);
}
