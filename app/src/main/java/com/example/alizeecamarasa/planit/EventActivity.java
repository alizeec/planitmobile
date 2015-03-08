package com.example.alizeecamarasa.planit;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.events.ChangeEvent;
import com.example.alizeecamarasa.planit.events.EventAPI;
import com.example.alizeecamarasa.planit.events.EventResponse;
import com.example.alizeecamarasa.planit.events.EventService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedInput;


public class EventActivity extends Hamburger {
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ( savedInstanceState == null ) {
            id = getIntent().getStringExtra("event_id");
            Bundle bundle = new Bundle();
            bundle.putString("event_id", id);

            //create main fragment : homepage of an event
            Fragment fragment = new EventFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame,fragment).commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EventService service = EventAPI.getInstance();
        switch (item.getItemId()) {
            case R.id.action_change_event:
                // implemented in fragment
                return false;
            case R.id.action_delete_event:
                service.deleteEvent(id,new Callback<EventResponse>() {
                    @Override
                    public void success(EventResponse eventResponse, Response response) {
                        Toast.makeText(EventActivity.this,"Événement supprimé",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
                return true;
            default:
                break;
        }

        return false;
    }
}
