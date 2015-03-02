package com.example.alizeecamarasa.planit.guest.TypeGuest;

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


import org.json.JSONException;
import org.json.JSONObject;


import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 19/02/15.
 */
public class AddTypeGuest extends Activity {


    private EditText label;
    private EditText price;
    private EditText message;
    private Button validate;

    private GuestModule module;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_type_guest);

        // get module in order to have his infos
        module = (GuestModule) getIntent().getSerializableExtra("module");

        // get fields
        label = (EditText) findViewById(R.id.inputTypeGuestLabel);
        price = (EditText) findViewById(R.id.inputTypeGuestPrice);
        message = (EditText) findViewById(R.id.inputTypeGuestMessage);
        LinearLayout layoutMessage = (LinearLayout) findViewById(R.id.layoutMessage);
        LinearLayout layoutPrice = (LinearLayout) findViewById(R.id.layoutPrice);
        validate = (Button) findViewById(R.id.validatenewtypeguest);
        if (module.isModuletype()){
            layoutMessage.setVisibility(View.INVISIBLE);
        }
        if (!module.isPayable()){
            layoutPrice.setVisibility(View.INVISIBLE);
        }

        //validate + save new type guest
        addTypeGuest();

    }

    private boolean isEmptyEditText(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean isEmptyTextView(TextView textview) {
        return textview.getText().toString().trim().length() == 0;
    }


    private void addTypeGuest() {

        validate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //if one of the field is empty, do nothing
                if (isEmptyEditText(label)) {
                    Toast.makeText(AddTypeGuest.this, R.string.create_event_error_msg, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (module.isPayable() && isEmptyEditText(price)){
                    Toast.makeText(AddTypeGuest.this, R.string.create_event_error_msg, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!module.isModuletype() && isEmptyTextView(message)){
                    Toast.makeText(AddTypeGuest.this, R.string.create_event_error_msg, Toast.LENGTH_SHORT).show();
                    return;
                }

                //else call the API to add type guest
                else {
                    JSONObject json = new JSONObject();
                    JSONObject typeguestJson = new JSONObject();
                    try {
                        typeguestJson.put("label",label.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (module.isPayable()){
                        try {
                            typeguestJson.put("price",Float.parseFloat(price.getText().toString()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if( !module.isModuletype()){
                        try {
                            typeguestJson.put("message",message.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        json.put("typeguest_form",typeguestJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());


                    TypeGuestService service = TypeGuestAPI.getInstance();
                    service.addTypeGuest(module.getId() , in, new Callback<Response>() {
                        @Override
                        public void success(Response event, Response response) {
                            Toast.makeText(AddTypeGuest.this, "Le type " + label.getText().toString() +" a été ajouté!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                            finish();
                        }

                    });
                }
            }
        });
    }

}
