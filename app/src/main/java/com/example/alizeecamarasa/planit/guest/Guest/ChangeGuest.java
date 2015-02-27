package com.example.alizeecamarasa.planit.guest.Guest;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
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
 * Created by alizeecamarasa on 17/02/15.
 */
public class ChangeGuest extends Activity {


    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private Button validate;
    private CheckBox payed;
    private SeekBar confirmed = null;
    int progressChanged = 0;
    int payedvalue;
    Guest guest;
    String type_guest;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_guest);

        guest = (Guest )getIntent().getSerializableExtra("guest");
        type_guest = getIntent().getStringExtra("type_guest");

        firstname = (EditText) findViewById(R.id.inputGuestFirstname);
        lastname = (EditText) findViewById(R.id.inputGuestLastname);
        email = (EditText) findViewById(R.id.inputGuestEmail);
        payed = (CheckBox)findViewById(R.id.payed);
        confirmed = (SeekBar) findViewById(R.id.seekbar1);
        payed.setChecked(true);

        validate = (Button) findViewById(R.id.changeGuest);

        // put current values
        firstname.setText(guest.getFirstname());
        lastname.setText(guest.getLastname());
        email.setText(guest.getEmail());
        if(guest.getPayed() == 0)
            payed.setChecked(false);
        else if (guest.getPayed() == 1)
            payed.setChecked(true);

        confirmed.setProgress(guest.getConfirmed());

        changeGuest();

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

    private void changeGuest() {
        confirmed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                switch (progressChanged){
                    case 0:
                        Toast.makeText(ChangeGuest.this,R.string.confirmed_no,Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(ChangeGuest.this,R.string.confirmed_yes,Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(ChangeGuest.this,R.string.confirmed_waiting,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        validate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //if one of the field is empty, do nothing
                if (isEmptyEditText(firstname) || isEmptyTextView(lastname) || isEmptyTextView(email) ) {
                    Toast.makeText(ChangeGuest.this, R.string.create_event_error_msg, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidEmail(email.getText().toString())){
                    Toast.makeText(ChangeGuest.this, R.string.error_email, Toast.LENGTH_SHORT).show();
                    return;
                }

                //else change the guest in BDD
                else {

                    if( payed.isChecked())
                        payedvalue = 1;
                    else
                        payedvalue = 0;

                    JSONObject json = new JSONObject();
                    JSONObject guestJson = new JSONObject();
                    try {
                        guestJson.put("type",type_guest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                        guestJson.put("payed",payedvalue);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        guestJson.put("confirmed",progressChanged);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        guestJson.put("sent",guest.getSent());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        guestJson.put("paymentmean",guest.getPaymentmean().getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        json.put("guestupdate_form",guestJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());

                    GuestService service = GuestAPI.getInstance();
                    service.modifyGuest(guest.getId(),in, new Callback<Response>() {
                        @Override
                        public void success(Response event, Response response) {
                            Toast.makeText(ChangeGuest.this, "L'invité "+guest.getFirstname()+" "+guest.getLastname()+" a été modifié!", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            System.out.println("echec");
                            error.printStackTrace();
                        }

                    });



                }
            }
        });
    }

}
