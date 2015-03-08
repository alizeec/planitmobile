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
 * Created by alizeecamarasa on 08/03/15.
 */
public class ChangeTypeGuest extends Activity {


    private EditText label;
    private EditText price;
    private EditText message;
    private Button change;
    private Button delete;

    private TypeGuest typeguest;
    private GuestModule module;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_type_guest);

        // get module in order to have his infos
        module = (GuestModule) getIntent().getSerializableExtra("module");
        typeguest = (TypeGuest) getIntent().getSerializableExtra("typeguest");

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(R.string.change_typeguest);


        // get fields
        label = (EditText) findViewById(R.id.inputTypeGuestLabel);
        price = (EditText) findViewById(R.id.inputTypeGuestPrice);
        message = (EditText) findViewById(R.id.inputTypeGuestMessage);
        LinearLayout layoutMessage = (LinearLayout) findViewById(R.id.layoutMessage);
        LinearLayout layoutPrice = (LinearLayout) findViewById(R.id.layoutPrice);
        change = (Button) findViewById(R.id.changetypeguest);
        delete = (Button) findViewById(R.id.deletetypeguest);

        // put current data
        label.setText(typeguest.getLabel());
        message.setText(typeguest.getMessage());
        price.setText(String.valueOf(typeguest.getPrice()));

        if (module.isModuletype()){
            layoutMessage.setVisibility(View.INVISIBLE);
        }
        if (!module.isPayable()){
            layoutPrice.setVisibility(View.INVISIBLE);
        }

        // hide button which are for add mode
        Button validate = (Button) findViewById(R.id.validatenewtypeguest);
        validate.setVisibility(View.GONE);

        changeTypeGuest();
        deleteTypeGuest();
    }



    private void changeTypeGuest() {

        change.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

            JSONObject json = new JSONObject();
            //JSONObject typeguestJson = new JSONObject();
            try {
                json.put("name",label.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (module.isPayable()){
                try {
                    json.put("price",Float.parseFloat(price.getText().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    json.put("type","payable");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    json.put("type","notpayable");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            /*if( !module.isModuletype()){
                try {
                    json.put("message",message.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/


            TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());

            TypeGuestService service = TypeGuestAPI.getInstance();
            service.changeTypeGuest(typeguest.getId() , in, new Callback<Response>() {
                @Override
                public void success(Response event, Response response) {
                    Toast.makeText(ChangeTypeGuest.this, "Le type " + label.getText().toString() +" a été modifié!", Toast.LENGTH_SHORT).show();
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

    public void deleteTypeGuest(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypeGuestService service = TypeGuestAPI.getInstance();
                service.deleteTypeGuest(typeguest.getId(), new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Toast.makeText(ChangeTypeGuest.this, "Le type " + label.getText().toString() + " a été supprimé!", Toast.LENGTH_SHORT).show();
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
