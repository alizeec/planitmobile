package com.example.alizeecamarasa.planit.transport;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.place.ChangePlace;
import com.example.alizeecamarasa.planit.place.Place;
import com.example.alizeecamarasa.planit.place.PlaceModuleAPI;
import com.example.alizeecamarasa.planit.place.PlaceModuleService;
import com.example.alizeecamarasa.planit.place.SeePlace;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by alizeecamarasa on 10/03/15.
 */
public class SeeTransport extends Activity {

    String name;
    Transport transport;
    Activity context;
    String contract_path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transport = (Transport)getIntent().getSerializableExtra("transport");
        context = this;
        setContentView(R.layout.see_transport);
    }

    @Override
    public void onStart(){
        super.onStart();
        chargeTransportView();
    }

    public void chargeTransportView(){
        TextView txtName = (TextView) findViewById(R.id.title);
        TextView txtState = (TextView) findViewById(R.id.state);
        TextView txtWebsite = (TextView) findViewById(R.id.website);
        TextView txtTel = (TextView) findViewById(R.id.tel);
        TextView txtCapacity = (TextView) findViewById(R.id.capacity);

        Button back = (Button) findViewById(R.id.back);
        Button modify = (Button) findViewById(R.id.modify);
        Button delete = (Button) findViewById(R.id.delete);
        Button contractButton = (Button) findViewById(R.id.contract);
        if(transport.getContract()==null){
            contractButton.setVisibility(View.GONE);
        }
        else {
            contract_path = transport.getContract();
        }

        name = transport.getName();
        float price = transport.getPrice();
        int state = transport.getState();
        String website = transport.getWebsite();
        String tel = transport.getTel();
        Float capacity = transport.getCapacity();

        txtName.setText(name+"   "+price+" €");

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


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteTransport();
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent= new Intent(context,ChangeTransport.class);
                intent.putExtra("transport",transport);
                startActivityForResult(intent, 1);
            }
        });

        contractButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DownloadContract mTask = new DownloadContract();
                mTask.execute();
            }
        });
    }

    public void deleteTransport(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez vous supprimer cette compagnie ?");
        builder.setCancelable(false);
        builder.setPositiveButton("OUI",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TransportModuleService service = TransportModuleAPI.getInstance();
                        service.deleteTransport(transport.getId(), new Callback<Response>() {
                            @Override
                            public void success(Response o, Response response) {
                                Toast.makeText(SeeTransport.this, "Compagnie supprimé", Toast.LENGTH_SHORT).show();
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

    class DownloadContract extends AsyncTask<Void, Void,String> {

        @Override
        protected String doInBackground(Void... params) {
            Uri myUri = Uri.parse("http://planit.marion-lecorre.com/web/files/transportation/contracts/"+contract_path);
            DownloadManager.Request r = new DownloadManager.Request(myUri);
            r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "contrat-"+contract_path);
            r.allowScanningByMediaScanner();
            r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            dm.enqueue(r);
            return "contract"+contract_path;
        }

        @Override
        protected void onPostExecute(String contrat) {
            if(contrat!=null)
                Toast.makeText(SeeTransport.this,"Le contrat "+contrat+" a été enregistré dans vos téléchargements",Toast.LENGTH_SHORT).show();
        }
    }
}

