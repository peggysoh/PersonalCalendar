package com.sealteamsix.personalcalendar;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class AddEvent extends AppCompatActivity implements View.OnClickListener {

    private CheckBox check1;
    private Button button_save;
    private TextView startTime, endTime, title;
    private TimePicker startPicker, endPicker;
    private EditText etName, etLocation, etDescription, etParticipants;
    private EventDbHelper eventDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context = this;
    private int date, month, year;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        title = (TextView) findViewById(R.id.addEvent_title);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        startPicker = (TimePicker) findViewById(R.id.timePicker_start);
        endPicker = (TimePicker) findViewById(R.id.timePicker_end);
        check1 = (CheckBox) findViewById(R.id.allDayCheck);
        check1.setOnClickListener(this);
        button_save = (Button) findViewById(R.id.saveBtn);
        button_save.setOnClickListener(this);
        etName = (EditText) findViewById(R.id.name);
        etLocation = (EditText) findViewById(R.id.location);
        etDescription = (EditText) findViewById(R.id.description);
        etParticipants = (EditText) findViewById(R.id.participants);

        // Getting date, month, year
        Intent myIntent = getIntent();
        date = myIntent.getIntExtra("date", 0);
        month = myIntent.getIntExtra("month", 0);
        year = myIntent.getIntExtra("year", 0);
        title.setText("Add Event for " + month + "/" + date + "/" + year);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // Enabling and disabling time picker
            case R.id.allDayCheck:
                CheckBox c = (CheckBox) view;
                if (c.isChecked()) {
                    Toast.makeText(this, "Set as all day event.", Toast.LENGTH_SHORT).show();
                    startTime.setEnabled(false);
                    startPicker.setEnabled(false);
                    endTime.setEnabled(false);
                    endPicker.setEnabled(false);
                } else {
                    Toast.makeText(this, "Pick start and end time.", Toast.LENGTH_SHORT).show();
                    startTime.setEnabled(true);
                    startPicker.setEnabled(true);
                    endTime.setEnabled(true);
                    endPicker.setEnabled(true);
                }
                break;

            // User clicked save
            case R.id.saveBtn:
                int startHour, startMin, endHour, endMin;

                // Getting event details from user
                String name = etName.getText().toString();
                String location = etLocation.getText().toString();
                String description = etDescription.getText().toString();
                String participants = etParticipants.getText().toString();
                if (check1.isChecked() == true) { //all day event
                    startHour = -1;
                    startMin = -1;
                    endHour = -1;
                    endMin = -1;
                }
                else {
                    startHour = startPicker.getHour();
                    startMin = startPicker.getMinute();
                    endHour = endPicker.getHour();
                    endMin = endPicker.getMinute();
                }

                // Insert event information into database
                eventDbHelper = new EventDbHelper(context);
                sqLiteDatabase = eventDbHelper.getWritableDatabase();
                eventDbHelper.addInfo(date, month, year, name, location, startHour, startMin,
                        endHour, endMin, description, participants, sqLiteDatabase);

                // Event added
                Toast.makeText(this, "Event added.", Toast.LENGTH_SHORT).show();
                eventDbHelper.close();

                setResult(RESULT_OK, null);
                finish();
                break;

            default:
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AddEvent Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.sealteamsix.personalcalendar/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }


    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AddEvent Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.sealteamsix.personalcalendar/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
