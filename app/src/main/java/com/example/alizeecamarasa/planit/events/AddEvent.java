package com.example.alizeecamarasa.planit.events;

        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.Locale;

        import android.app.Activity;
        import android.app.DatePickerDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.alizeecamarasa.planit.BaseActivity;
        import com.example.alizeecamarasa.planit.HomeActivity;
        import com.example.alizeecamarasa.planit.R;

        import org.json.JSONException;
        import org.json.JSONObject;

        import retrofit.Callback;
        import retrofit.RetrofitError;
        import retrofit.client.Response;
        import retrofit.mime.TypedByteArray;
        import retrofit.mime.TypedInput;


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

    //creation of the event : validation + creation in database via parse
    private void createEvent() {
        validate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //if one of the field is empty, do nothing
                if (isEmptyEditText(eventName) || isEmptyTextView(dateEventBegin) || isEmptyTextView(dateEventEnd) ||  isEmptyEditText(eventDescription)) {
                    Toast.makeText(AddEvent.this, R.string.create_event_error_msg, Toast.LENGTH_SHORT).show();
                    return;
                }

                //else create parseObject and enter it in database
                else {
                    // ajout de l'événement
                    JSONObject json = new JSONObject();
                    JSONObject eventJson = new JSONObject();
                    try {
                        eventJson.put("name",eventName.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        eventJson.put("description",eventDescription.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        eventJson.put("begin_date",dateEventBegin.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        eventJson.put("end_date",dateEventEnd.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        eventJson.put("image","image.jpg");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        json.put("event_form",eventJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());



                    EventService service = EventAPI.getInstance();
                    service.addEvent(3,in, new Callback<JSONObject>() {
                        @Override
                        public void success(JSONObject event, Response response) {
                            System.out.print(response);

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            System.out.println("echec");
                            error.printStackTrace();
                        }
                    });


                    Toast.makeText(AddEvent.this, "Événement "+eventName.getText().toString()+" créé!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                }
            }
        });
    }

}

