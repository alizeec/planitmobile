package com.example.alizeecamarasa.planit.place;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.budget.Item.ItemBudget;
import com.squareup.picasso.Picasso;

/**
 * Created by alizeecamarasa on 01/03/15.
 */
public class SeePlace extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Place place = (Place)getIntent().getSerializableExtra("place");

        setContentView(R.layout.see_place);
        chargePlaceView(place);

    }

    public void chargePlaceView(Place place){

        TextView txtName = (TextView) findViewById(R.id.title);
        TextView txtAdress = (TextView) findViewById(R.id.adress);
        TextView txtDistance = (TextView) findViewById(R.id.distance);
        TextView txtState = (TextView) findViewById(R.id.state);
        TextView txtWebsite = (TextView) findViewById(R.id.website);
        TextView txtTel = (TextView) findViewById(R.id.tel);
        TextView txtCapacity = (TextView) findViewById(R.id.capacity);
        ImageView imageview = (ImageView) findViewById(R.id.image);
        Button back = (Button) findViewById(R.id.back);

        String name = place.getName();
        float price = place.getPrice();
        String adress = place.getAdress();
        float distance = place.getDistance();
        int state = place.getState();
        String website = place.getWebsite();
        String tel = place.getTel();
        Float capacity = place.getCapacity();

        System.out.println(place.getAdress());
        System.out.println(place.getImage());


        txtName.setText(name+"   "+price+" €");
        txtAdress.setText(adress);

        txtDistance.setText(String.valueOf(distance)+" km");
        if (state == 0)
            txtState.setText("Refusé");
        else if (state == 1)
            txtState.setText("Choisi");
        else if (state ==2)
            txtState.setText("Contacté");

        txtWebsite.setText(website);
        txtTel.setText(tel);
        txtCapacity.setText(String.valueOf(capacity)+" personnes");

        Picasso.with(this).load("http://planit.marion-lecorre.com/images/place/places_pictures/"+place.getImage())
                .error(R.drawable.no_image)
                .into(imageview);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
