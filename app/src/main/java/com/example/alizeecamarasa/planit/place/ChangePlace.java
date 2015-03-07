package com.example.alizeecamarasa.planit.place;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
 * Created by alizeecamarasa on 06/03/15.
 */
public class ChangePlace extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Place place = (Place)getIntent().getSerializableExtra("place");
        setContentView(R.layout.change_place);
        changePlace(place);
    }

    private boolean isEmptyEditText(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean isEmptyTextView(TextView textview) {
        return textview.getText().toString().trim().length() == 0;
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.state_place, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    // IF THE ITEM IS AN INFLOW
    public void changePlace(Place place){
        final Place mPlace = place;

        final TextView txtName = (TextView) findViewById(R.id.name);
        final TextView txtPrice = (TextView) findViewById(R.id.price);
        final TextView txtAddress = (TextView) findViewById(R.id.adress);
        final TextView txtDistance = (TextView) findViewById(R.id.distance);
        final TextView txtWebsite = (TextView) findViewById(R.id.website);
        final TextView txtTel = (TextView) findViewById(R.id.tel);
        final TextView txtCapacity = (TextView) findViewById(R.id.capacity);
        final TextView txtRemark = (TextView) findViewById(R.id.remark);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_state);

        Button back = (Button) findViewById(R.id.back);
        Button validate = (Button) findViewById(R.id.validate);

        String name = place.getName();
        String address = place.getAddress();
        String website = place.getWebsite();
        String tel = place.getTel();
        String remark = place.getRemark();
        float price = place.getPrice();
        float distance = place.getDistance();
        float capacity = place.getCapacity();

        // on mets les valeurs actuelles
        txtName.setText(name);
        txtAddress.setText(address);
        txtWebsite.setText(website);
        txtTel.setText(tel);
        txtRemark.setText(remark);
        txtPrice.setText(String.valueOf(price));
        txtDistance.setText(String.valueOf(distance));
        txtCapacity.setText(String.valueOf(capacity));
        addItemsOnSpinner(spinner);
        spinner.setSelection(place.getState());

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
            JSONObject placeJson = new JSONObject();
            try {
                placeJson.put("name", txtName.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                placeJson.put("address", txtAddress.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                placeJson.put("price", Float.parseFloat(txtPrice.getText().toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                placeJson.put("capacity", Float.parseFloat(txtCapacity.getText().toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                placeJson.put("distance", Float.parseFloat(txtDistance.getText().toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                placeJson.put("tel",txtTel.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                placeJson.put("website",txtWebsite.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                placeJson.put("remark",txtRemark.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                placeJson.put("state",spinner.getSelectedItemPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                json.put("place_form",placeJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());
            PlaceModuleService service = PlaceModuleAPI.getInstance();
            service.modifyPlace(mPlace.getId(), in, new Callback<Response>() {
                @Override
                public void success(Response event, Response response) {
                    Toast.makeText(ChangePlace.this, "Le lieu " + txtName.getText().toString() +" a été modifiée!", Toast.LENGTH_SHORT).show();
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
