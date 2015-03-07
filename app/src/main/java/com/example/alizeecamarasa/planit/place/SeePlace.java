package com.example.alizeecamarasa.planit.place;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;



import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by alizeecamarasa on 01/03/15.
 */
public class SeePlace extends FragmentActivity implements OnMapReadyCallback {
    double Coordlat;
    double Coordlong;
    LatLng coordinates;
    String name;
    Place place;
    Activity context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        place = (Place)getIntent().getSerializableExtra("place");
        context = this;
        setContentView(R.layout.see_place);
    }

    @Override
    public void onStart(){
        super.onStart();
        chargePlaceView();
    }

    public void chargePlaceView(){

        TextView txtName = (TextView) findViewById(R.id.title);
        TextView txtAddress = (TextView) findViewById(R.id.adress);
        TextView txtDistance = (TextView) findViewById(R.id.distance);
        TextView txtState = (TextView) findViewById(R.id.state);
        TextView txtWebsite = (TextView) findViewById(R.id.website);
        TextView txtTel = (TextView) findViewById(R.id.tel);
        TextView txtCapacity = (TextView) findViewById(R.id.capacity);
        ImageView imageview = (ImageView) findViewById(R.id.image);

        Button back = (Button) findViewById(R.id.back);
        Button modify = (Button) findViewById(R.id.modify);
        Button delete = (Button) findViewById(R.id.delete);

        name = place.getName();
        float price = place.getPrice();
        String adress = String.valueOf(place.getAddress());
        float distance = place.getDistance();
        int state = place.getState();
        String website = place.getWebsite();
        String tel = place.getTel();
        Float capacity = place.getCapacity();
        Coordlat = place.getLatitude();
        Coordlong = place.getLongitude();


        txtName.setText(name+"   "+price+" €");
        txtAddress.setText(adress);

        txtDistance.setText(String.valueOf(distance)+" km");
        if (state == 0)
            txtState.setText("Refusé");
        else if (state == 1)
            txtState.setText("Choisi");
        else if (state ==2)
            txtState.setText("Contacté");
        else if (state == 3)
            txtState.setText("A contacter");

        txtWebsite.setText(website);
        txtTel.setText(tel);
        txtCapacity.setText(String.valueOf(capacity)+" personnes");

        Picasso.with(this).load("http://planit.marion-lecorre.com/images/place/places_pictures/"+place.getImage())
                .error(R.drawable.no_image_place)
                .into(imageview);

        // on teste avec les longitude et latitude
        if (Coordlat != 0 && Coordlong != 0)
            coordinates = new LatLng(Coordlat, Coordlong);
        if (coordinates!= null && !coordinates.toString().equals("lat/lng: (0.0,0.0)")){
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        // si invalide, on  teste avec l'adresse
        else {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocationName(adress, 1);
                if(addresses.size() > 0) {
                    Coordlat= addresses.get(0).getLatitude();
                    Coordlong= addresses.get(0).getLongitude();
                    coordinates = new LatLng(Coordlat, Coordlong);
                    MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deletePlace();
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent= new Intent(context,ChangePlace.class);
                intent.putExtra("place",place);
                startActivityForResult(intent, 1);
            }
        });





        //Intent searchAddress = new  Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + adress));
        //startActivity(searchAddress);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng place = new LatLng(Coordlat, Coordlong);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 3));

        map.addMarker(new MarkerOptions()
                .title("Votre choix")
                .snippet(name)
                .position(place));
    }

    public void deletePlace(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez vous supprimer ce lieu?");
        builder.setCancelable(false);
        builder.setPositiveButton("OUI",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PlaceModuleService service = PlaceModuleAPI.getInstance();
                        service.deletePlace(place.getId(), new Callback<Response>() {
                            @Override
                            public void success(Response o, Response response) {
                                Toast.makeText(SeePlace.this,"Lieu supprimé",Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                error.printStackTrace();
                            }
                        });
                    }
                });
        builder.setNegativeButton("NON",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            finish();
        }
    }
}
