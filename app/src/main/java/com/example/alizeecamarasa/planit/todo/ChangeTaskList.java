package com.example.alizeecamarasa.planit.todo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.guest.GuestModule;
import com.example.alizeecamarasa.planit.guest.TypeGuest.TypeGuest;
import com.example.alizeecamarasa.planit.guest.TypeGuest.TypeGuestAPI;
import com.example.alizeecamarasa.planit.guest.TypeGuest.TypeGuestService;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 08/03/15.
 */
public class ChangeTaskList extends Activity {


    private EditText label;
    private Button change;
    private Button delete;

    private CategoryTask category;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_category_task);

        // get module in order to have his infos
        category = (CategoryTask) getIntent().getSerializableExtra("category");

        // get fields
        label = (EditText) findViewById(R.id.name);
        change = (Button) findViewById(R.id.change);
        delete = (Button) findViewById(R.id.delete);

        // put current data
        label.setText(category.getName());

        changeCategory();
        deleteCategory();
    }



    private void changeCategory() {

        change.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                JSONObject json = new JSONObject();
                try {
                    json.put("name",label.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());
                System.out.println(json);


                TodoModuleService service = TodoModuleAPI.getInstance();
                service.changeCategoryTask(category.getId() , in, new Callback<Response>() {
                    @Override
                    public void success(Response event, Response response) {
                        Toast.makeText(ChangeTaskList.this, "La catégorie " + label.getText().toString() + " a été modifié!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                        finish();
                    }

                });

            }
        });
    }

    public void deleteCategory(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoModuleService service = TodoModuleAPI.getInstance();
                service.deleteCategoryTask(category.getId(), new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Toast.makeText(ChangeTaskList.this, "La catégorie " + label.getText().toString() + " a été supprimé!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
            }
        });
    }

}
