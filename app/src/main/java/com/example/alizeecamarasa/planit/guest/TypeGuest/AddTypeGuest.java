package com.example.alizeecamarasa.planit.guest.TypeGuest;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.guest.Guest.GuestAPI;
import com.example.alizeecamarasa.planit.guest.Guest.GuestService;
import com.example.alizeecamarasa.planit.guest.GuestModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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

    private String id_module;
    private Boolean mode;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_type_guest);

        // get module in order to have his id
        id_module = getIntent().getStringExtra("id_module");
        mode = getIntent().getBooleanExtra("mode",false);




        // get fields
        label = (EditText) findViewById(R.id.inputTypeGuestLabel);
        price = (EditText) findViewById(R.id.inputTypeGuestPrice);
        message = (EditText) findViewById(R.id.inputTypeGuestMessage);
        LinearLayout layoutMessage = (LinearLayout) findViewById(R.id.layoutMessage);
        validate = (Button) findViewById(R.id.validatenewtypeguest);
        if (mode == true){
            layoutMessage.setVisibility(View.INVISIBLE);
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


    //creation of the event : validation + creation in database via parse
    private void addTypeGuest() {

        // clic sur le bouton valider
        validate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                //if one of the field is empty, do nothing
                if (isEmptyEditText(label) || isEmptyTextView(price)) {
                    Toast.makeText(AddTypeGuest.this, R.string.create_event_error_msg, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mode == false && isEmptyTextView(message)){
                    Toast.makeText(AddTypeGuest.this, R.string.create_event_error_msg, Toast.LENGTH_SHORT).show();
                    return;
                }

                //else create parseObject and enter it in database
                else {

                    // ajout du type
                    JSONObject json = new JSONObject();
                    JSONObject typeguestJson = new JSONObject();
                    try {
                        typeguestJson.put("label",label.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        typeguestJson.put("price",Float.parseFloat(price.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if( mode == false){
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
                    service.addTypeGuest(id_module , in, new Callback<JSONObject>() {
                        @Override
                        public void success(JSONObject event, Response response) {
                            Toast.makeText(AddTypeGuest.this, "Le type " + label.getText().toString() +" a été ajouté!", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                        }

                    });
                    finish();
                }
            }
        });
    }

}
