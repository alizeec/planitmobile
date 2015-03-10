package com.example.alizeecamarasa.planit.transport;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.place.Place;
import com.example.alizeecamarasa.planit.place.PlaceModuleAPI;
import com.example.alizeecamarasa.planit.place.PlaceModuleService;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 10/03/15.
 */
public class ChangeTransport extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Transport transport = (Transport)getIntent().getSerializableExtra("transport");
        setContentView(R.layout.change_transport);
        changeTransport(transport);
    }

    private boolean isEmptyEditText(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean isEmptyTextView(TextView textview) {
        return textview.getText().toString().trim().length() == 0;
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.state_logistic, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    // IF THE ITEM IS AN INFLOW
    public void changeTransport(Transport transport){
        final Transport mTransport = transport;

        final TextView txtName = (TextView) findViewById(R.id.name);
        final TextView txtPrice = (TextView) findViewById(R.id.price);
        final TextView txtWebsite = (TextView) findViewById(R.id.website);
        final TextView txtTel = (TextView) findViewById(R.id.tel);
        final TextView txtCapacity = (TextView) findViewById(R.id.capacity);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_state);

        Button back = (Button) findViewById(R.id.back);
        Button validate = (Button) findViewById(R.id.validate);

        String name = transport.getName();
        String website = transport.getWebsite();
        String tel = transport.getTel();
        float price = transport.getPrice();
        float capacity = transport.getCapacity();

        // on mets les valeurs actuelles
        txtName.setText(name);
        txtWebsite.setText(website);
        txtTel.setText(tel);
        txtPrice.setText(String.valueOf(price));
        txtCapacity.setText(String.valueOf(capacity));
        addItemsOnSpinner(spinner);
        spinner.setSelection(transport.getState());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // modif du lieu

                JSONObject json = new JSONObject();
                JSONObject transportJson = new JSONObject();
                try {
                    transportJson.put("name", txtName.getText().toString());
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
                    transportJson.put("tel",txtTel.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    transportJson.put("website",txtWebsite.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    transportJson.put("state",spinner.getSelectedItemPosition());
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
                service.modifyTransport(mTransport.getId(), in, new Callback<Response>() {
                    @Override
                    public void success(Response event, Response response) {
                        Toast.makeText(ChangeTransport.this, "La compagnie " + txtName.getText().toString() + " a été modifiée!", Toast.LENGTH_SHORT).show();
                        setResult(1);
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

