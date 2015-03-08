package com.example.alizeecamarasa.planit.place;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.events.EventAPI;
import com.example.alizeecamarasa.planit.events.EventService;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by alizeecamarasa on 08/03/15.
 */
public class AddPlace extends Activity {

    private EditText txtName;
    private EditText txtPrice;
    private EditText txtAddress;
    private EditText txtDistance;
    private EditText txtWebsite;
    private EditText txtTel;
    private EditText txtCapacity;
    private EditText txtRemark;
    private Button validate;
    private TypedFile typedFile;
    private double latitude;
    private double longitude;
    private String id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_place);
        id = getIntent().getStringExtra("id");

        txtName = (EditText) findViewById(R.id.name);
        txtPrice = (EditText) findViewById(R.id.price);
        txtAddress = (EditText) findViewById(R.id.address);
        txtDistance = (EditText) findViewById(R.id.distance);
        txtWebsite = (EditText) findViewById(R.id.website);
        txtTel = (EditText) findViewById(R.id.phone);
        txtCapacity = (EditText) findViewById(R.id.capacity);
        txtRemark = (EditText) findViewById(R.id.remark);
        validate = (Button) findViewById(R.id.validatenewplace);

        //validate + save new place
        createPlace();

    }


    private boolean isEmptyEditText(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean isEmptyTextView(TextView textview) {
        return textview.getText().toString().trim().length() == 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        String selectedImagePath = null;
        Uri selectedImageUri = data.getData();
        Cursor cursor = this.getContentResolver().query(selectedImageUri, null, null,null, null);
        if (cursor == null) {
            selectedImagePath = selectedImageUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            selectedImagePath = cursor.getString(idx);
        }
        File photo = new File(selectedImagePath);
        typedFile = new TypedFile("application/octet-stream", photo);


    }

    //creation of the place : validation + add in BDD
    private void createPlace() {
        Button image = (Button) findViewById(R.id.inputImage);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent photoLibraryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                photoLibraryIntent.setType("image/*");
                startActivityForResult(photoLibraryIntent, 1);
            }
        });

        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(txtAddress.getText().toString(), 1);
            if(addresses.size() > 0) {
                latitude= addresses.get(0).getLatitude();
                longitude= addresses.get(0).getLongitude();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        validate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //if one of the field is empty, do nothing
                if (isEmptyEditText(txtName) || isEmptyTextView(txtAddress) ) {
                    Toast.makeText(AddPlace.this, R.string.error_msg_all_fields, Toast.LENGTH_SHORT).show();
                    return;
                }

                //else create event and enter it in database
                else {
                    PlaceModuleService service = PlaceModuleAPI.getInstance();
                    service.addPlace(id,txtName.getText().toString(),txtAddress.getText().toString(),txtTel.getText().toString(),txtDistance.getText().toString(),txtPrice.getText().toString(),txtCapacity.getText().toString(),txtWebsite.getText().toString(),String.valueOf(latitude),String.valueOf(longitude),txtRemark.getText().toString(),typedFile,  new Callback<Response>() {
                        @Override
                        public void success(Response response, Response response2) {
                            Toast.makeText(AddPlace.this,"Le lieu "+txtName.getText().toString()+" a bien été ajouté",Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            finish();
                            error.printStackTrace();
                        }
                    });
                }
            }
        });
    }

}


