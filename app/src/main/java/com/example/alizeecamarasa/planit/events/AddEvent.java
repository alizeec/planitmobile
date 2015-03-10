package com.example.alizeecamarasa.planit.events;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


public class AddEvent extends Activity {
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



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        eventName = (EditText) findViewById(R.id.inputEventName);
        eventDescription = (EditText) findViewById(R.id.inputEventDescription);
        validate = (Button) findViewById(R.id.validateNewEvent);

        //manage date picker
        manageDatePicker();

        //validate + save new event
        createEvent();

    }





    private void manageDatePicker() {
        dateEventBegin = (TextView) findViewById(R.id.inputEventDateBegin);
        dateEventEnd = (TextView) findViewById(R.id.inputEventDateEnd);
        calendarBegin = Calendar.getInstance();
        calendarEnd = Calendar.getInstance();
        calendarIconBegin = (ImageButton) findViewById(R.id.dateCalendarBegin);
        calendarIconEnd = (ImageButton) findViewById(R.id.dateCalendarEnd);

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
                new DatePickerDialog(AddEvent.this, dateBegin, calendarBegin.get(Calendar.YEAR), calendarBegin.get(Calendar.MONTH), calendarBegin.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        calendarIconEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddEvent.this, dateEnd, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
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
        System.out.println(typedFile);


    }

    //creation of the event : validation + add in BDD
    private void createEvent() {
        Button image = (Button) findViewById(R.id.inputImage);
        image.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 Intent photoLibraryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 photoLibraryIntent.setType("image/*");
                 startActivityForResult(photoLibraryIntent, 1);
             }
         });

        validate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //if one of the field is empty, do nothing
                if (isEmptyEditText(eventName) || isEmptyTextView(dateEventBegin) || isEmptyTextView(dateEventEnd) ||  isEmptyEditText(eventDescription)) {
                    Toast.makeText(AddEvent.this, R.string.error_msg_all_fields, Toast.LENGTH_SHORT).show();
                    return;
                }

                //else create event and enter it in database
                else {
                    EventService service = EventAPI.getInstance();
                    service.addEvent("1",eventName.getText().toString(),eventDescription.getText().toString(),dateEventBegin.getText().toString(),dateEventEnd.getText().toString(),typedFile,  new Callback<Response>() {
                        @Override
                        public void success(Response response, Response response2) {
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

