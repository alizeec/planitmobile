package com.example.alizeecamarasa.planit.events;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.place.Place;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 08/03/15.
 */
public class ChangeEvent extends Activity {
    private Calendar calendarBegin;
    private Calendar calendarEnd;
    private TextView dateEventBegin;
    private TextView dateEventEnd;
    private ImageButton calendarIconBegin;
    private ImageButton calendarIconEnd;
    private EditText eventName;
    private EditText eventDescription;
    private Button validate;
    private Bitmap imagefile;
    private TypedFile typedFile;
    private Event event;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);
        event  = (Event)getIntent().getSerializableExtra("event");

        // put current data
        eventName = (EditText) findViewById(R.id.inputEventName);
        eventName.setText(event.getName());
        eventDescription = (EditText) findViewById(R.id.inputEventDescription);
        eventDescription.setText(event.getDescription());

        // hide the image button
        LinearLayout image = (LinearLayout) findViewById(R.id.image_layout);
        image.setVisibility(View.INVISIBLE);

        // change the label of the button
        validate = (Button) findViewById(R.id.validateNewEvent);
        validate.setText("Modifier");

        //manage date picker
        manageDatePicker();

        //validate + save new event
        changeEvent();

    }


    private void manageDatePicker() {
        dateEventBegin = (TextView) findViewById(R.id.inputEventDateBegin);
        dateEventEnd = (TextView) findViewById(R.id.inputEventDateEnd);
        calendarBegin = Calendar.getInstance();
        calendarEnd = Calendar.getInstance();
        calendarIconBegin = (ImageButton) findViewById(R.id.dateCalendarBegin);
        calendarIconEnd = (ImageButton) findViewById(R.id.dateCalendarEnd);

        // put current data
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateEventBegin.setText(sdf.format(event.getBeginDate()));
        dateEventEnd.setText(sdf.format(event.getEndDate()));

        final DatePickerDialog.OnDateSetListener dateBegin = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendarBegin.set(Calendar.YEAR, year);
                calendarBegin.set(Calendar.MONTH, monthOfYear);
                calendarBegin.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(0);
            }

        };
        final DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, monthOfYear);
                calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(1);
            }

        };

        calendarIconBegin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(ChangeEvent.this, dateBegin, calendarBegin.get(Calendar.YEAR), calendarBegin.get(Calendar.MONTH), calendarBegin.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        calendarIconEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(ChangeEvent.this, dateEnd, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel(Integer val) {

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if(val == 0){
            dateEventBegin.setText(sdf.format(calendarBegin.getTime()));
        }

        else if(val == 1){
            dateEventEnd.setText(sdf.format(calendarEnd.getTime()));
        }
    }



    //creation of the event : validation + add in BDD
    private void changeEvent() {

        validate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                JSONObject json = new JSONObject();
                JSONObject eventJson = new JSONObject();
                try {
                    eventJson.put("name", eventName.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    eventJson.put("description", eventDescription.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    eventJson.put("begin_date", dateEventBegin.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    eventJson.put("end_date", dateEventEnd.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    json.put("event_form", eventJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());
                EventService service = EventAPI.getInstance();
                service.updateEvent(event.getId(), in, new Callback<Response>() {
                    @Override
                    public void success(Response s, Response response) {
                        Toast.makeText(ChangeEvent.this, "L'événement a bien été modifié!", Toast.LENGTH_SHORT).show();
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

