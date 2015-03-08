package com.example.alizeecamarasa.planit.guest.Guest;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.guest.GuestModule;
import com.example.alizeecamarasa.planit.guest.TypeGuest.TypeGuest;

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
public class AddGuest extends Activity {


    private Spinner spinnerTypeGuest;
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private Button validate;

    private GuestModule mModule;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_guest);

        // get module in order to have typeguest list
        mModule = (GuestModule)getIntent().getSerializableExtra("module");

        // get fields
        firstname = (EditText) findViewById(R.id.inputGuestFirstname);
        lastname = (EditText) findViewById(R.id.inputGuestLastname);
        email = (EditText) findViewById(R.id.inputGuestEmail);
        validate = (Button) findViewById(R.id.validatenewguest);

        // set data in the spinner
        addItemsOnSpinner();

        //validate + save new guest
        addGuest();

    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {

        spinnerTypeGuest = (Spinner) findViewById(R.id.spinner);

        List<TypeGuest> list = mModule.getType_guest();


        ArrayAdapter<TypeGuest> dataAdapter = new ArrayAdapter<TypeGuest>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeGuest.setAdapter(dataAdapter);
    }



    private boolean isEmptyEditText(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean isEmptyTextView(TextView textview) {
        return textview.getText().toString().trim().length() == 0;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    //add guest in BDD from API
    private void addGuest() {
        validate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                TypeGuest type = (TypeGuest) spinnerTypeGuest.getSelectedItem();

                //if one of the field is empty, do nothing
                if (isEmptyEditText(firstname) || isEmptyTextView(lastname) || isEmptyTextView(email) ) {
                    Toast.makeText(AddGuest.this, R.string.error_msg_all_fields, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidEmail(email.getText().toString())){
                    Toast.makeText(AddGuest.this, R.string.error_email, Toast.LENGTH_SHORT).show();
                    return;
                }

                //else create guest
                else {
                    JSONObject json = new JSONObject();
                    JSONObject guestJson = new JSONObject();
                    try {
                        guestJson.put("firstname",firstname.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        guestJson.put("lastname",lastname.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        guestJson.put("email",email.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        json.put("guest_form",guestJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());

                    GuestService service = GuestAPI.getInstance();
                    service.addGuest(type.getId(), in, new Callback<Response>() {
                        @Override
                        public void success(Response event, Response response) {
                            Toast.makeText(AddGuest.this, "L'invité " + firstname.getText().toString() + " " + lastname.getText().toString() + " a été ajouté!", Toast.LENGTH_SHORT).show();
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
