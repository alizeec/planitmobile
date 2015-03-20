package com.example.alizeecamarasa.planit.transport;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 12/03/15.
 */
public class AddTransport extends Activity {
    String module_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        module_id = getIntent().getStringExtra("id");
        setContentView(R.layout.add_transport);
        addTransport();
    }

    private boolean isEmptyEditText(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }



    public void addTransport(){

        final EditText txtName = (EditText) findViewById(R.id.name);
        final EditText txtPrice = (EditText) findViewById(R.id.price);
        final EditText txtWebsite = (EditText) findViewById(R.id.website);
        final EditText txtTel = (EditText) findViewById(R.id.tel);
        final EditText txtCapacity = (EditText) findViewById(R.id.capacity);

        Button validate = (Button) findViewById(R.id.validatenewtransport);


        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEmptyEditText(txtName)){
                    Toast.makeText(AddTransport.this, R.string.error_msg_all_fields, Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    JSONObject json = new JSONObject();
                    JSONObject transportJson = new JSONObject();
                    try {
                        transportJson.put("name", txtName.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        transportJson.put("tel",txtTel.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        transportJson.put("price", Float.parseFloat(txtPrice.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        transportJson.put("capacity", Float.parseFloat(txtCapacity.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        transportJson.put("website",txtWebsite.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        transportJson.put("state","3");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        json.put("transportation_form",transportJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());
                    TransportModuleService service = TransportModuleAPI.getInstance();
                    service.addTransport(module_id, in, new Callback<Response>() {
                        @Override
                        public void success(Response event, Response response) {
                            Toast.makeText(AddTransport.this, "La compagnie " + txtName.getText().toString() + " a été ajoutée!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                        }
                    });
                }


            }
        });
    }
}


