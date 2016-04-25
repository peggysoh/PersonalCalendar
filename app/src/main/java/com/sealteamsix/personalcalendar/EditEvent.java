package com.sealteamsix.personalcalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class EditEvent extends AppCompatActivity implements View.OnClickListener {

    private CheckBox check;
    private Button button_save, button_delete, button_edit, button_share;
    private TextView startTime, endTime, title, allDayTxt, date, eventType;
    private TimePicker startPicker, endPicker;
    private EditText display_Name, display_Location, display_Description, display_Participants;
    private EventDbHelper eventDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private DatePicker datePicker;
    private Spinner drop_eventType;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        title = (TextView) findViewById(R.id.editEvent_title);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        date = (TextView) findViewById(R.id.date);
        startPicker = (TimePicker) findViewById(R.id.timePicker_start);
        endPicker = (TimePicker) findViewById(R.id.timePicker_end);
        check = (CheckBox) findViewById(R.id.allDayCheck);
        check.setOnClickListener(this);
        button_save = (Button) findViewById(R.id.saveBtn);
        button_delete = (Button) findViewById(R.id.deleteBtn);
        button_edit = (Button) findViewById(R.id.editBtn);
        button_share = (Button) findViewById(R.id.shareBtn);
        display_Name = (EditText) findViewById(R.id.display_name);
        display_Location = (EditText) findViewById(R.id.display_location);
        display_Description = (EditText) findViewById(R.id.display_description);
        display_Participants = (EditText) findViewById(R.id.display_participants);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        allDayTxt = (TextView) findViewById(R.id.allDayText);
        eventType = (TextView) findViewById(R.id.eventType);
        drop_eventType = (Spinner) findViewById(R.id.spinner_eventType);

        // Setting up event type dropdown menu
        String[] items = new String[]{"Holiday", "Birthday", "School", "Work", "Personal", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        drop_eventType.setAdapter(adapter);

        // Getting event
        Intent myIntent = getIntent();
        name = myIntent.getStringExtra("name");
        title.setText("Event Details");

        eventDbHelper = new EventDbHelper(getApplicationContext());
        sqLiteDatabase = eventDbHelper.getReadableDatabase();
        Cursor cursor = eventDbHelper.getEvent(name, sqLiteDatabase);

        // Get data from cursor
        // 0 DATE 1 MONTH 2 YEAR 3 LOCATION 4 START_HR 5 START_MIN
        // 6 END_HR 7 END_MIN 8 DESCRIPTION 9 PARTICIPANTS
        if (cursor.moveToFirst()) {
            Log.e("DATABASE OPERATIONS", "Event found...");
            int DATE = cursor.getInt(0);
            int MONTH = cursor.getInt(1);
            int YEAR = cursor.getInt(2);
            String LOCATION = cursor.getString(3);
            int START_HR = cursor.getInt(4);
            int START_MIN = cursor.getInt(5);
            int END_HR = cursor.getInt(6);
            int END_MIN = cursor.getInt(7);
            String DESCRIPTION = cursor.getString(8);
            String PARTICIPANTS = cursor.getString(9);
            String TYPE = cursor.getString(11);

            display_Name.setText(name);
            display_Location.setText(LOCATION);
            display_Description.setText(DESCRIPTION);
            display_Participants.setText(PARTICIPANTS);
            date.setText(" Date: " + MONTH + "/" + DATE + "/" + YEAR);
            datePicker.updateDate(YEAR, MONTH-1, DATE);
            eventType.setText(" Event Type: " + TYPE);

            if(TYPE.equals("Holiday"))
                drop_eventType.setSelection(0);
            else if(TYPE.equals("Birthday"))
                drop_eventType.setSelection(1);
            else if(TYPE.equals("School"))
                drop_eventType.setSelection(2);
            else if(TYPE.equals("Work"))
                drop_eventType.setSelection(3);
            else if(TYPE.equals("Personal"))
                drop_eventType.setSelection(4);
            else
                drop_eventType.setSelection(5);

            if (START_HR == -1) {
                check.setChecked(true);
                startPicker.setEnabled(false);
                endPicker.setEnabled(false);
                startTime.setText(" Time: All-Day");
            }
            else {
                startPicker.setEnabled(true);
                endPicker.setEnabled(true);
                startPicker.setHour(START_HR);
                startPicker.setMinute(START_MIN);
                endPicker.setHour(END_HR);
                endPicker.setMinute(END_MIN);

                if(START_HR == 12) {
                    if(END_HR == 12) {
                        startTime.setText(" Time: " + String.format("%02d", START_HR) + ":" + String.format("%02d", START_MIN) + "PM - " +
                                String.format("%02d", END_HR) + ":" + String.format("%02d", END_MIN) + "PM");
                    }
                    else if(END_HR > 12) {
                        startTime.setText(" Time: " + String.format("%02d", START_HR) + ":" + String.format("%02d", START_MIN) + "PM - " +
                                String.format("%02d", END_HR-12) + ":" + String.format("%02d", END_MIN) + "PM");
                    }
                    else if (END_HR == 00) {
                        startTime.setText(" Time: " + String.format("%02d", START_HR) + ":" + String.format("%02d", START_MIN) + "PM - " +
                                String.format("%02d", END_HR+12) + ":" + String.format("%02d", END_MIN) + "AM");
                    }
                    else {
                        startTime.setText(" Time: " + String.format("%02d", START_HR) + ":" + String.format("%02d", START_MIN) + "PM - " +
                                String.format("%02d", END_HR) + ":" + String.format("%02d", END_MIN) + "AM");
                    }
                }

                else if(START_HR == 00) {
                    if (END_HR == 12) {
                        startTime.setText(" Time: " + String.format("%02d",START_HR+12) + ":" + String.format("%02d", START_MIN) + "AM - " +
                                String.format("%02d", END_HR) + ":" + String.format("%02d", END_MIN) + "PM");
                    }
                    else if (END_HR > 12) {
                        startTime.setText(" Time: " + String.format("%02d", START_HR+12) + ":" + String.format("%02d", START_MIN) + "AM - " +
                                String.format("%02d", END_HR-12) + ":" + String.format("%02d", END_MIN) + "PM");
                    }
                    else if (END_HR == 00) {
                        startTime.setText(" Time: " + String.format("%02d", START_HR+12) + ":" + String.format("%02d", START_MIN) + "AM - " +
                                String.format("%02d", END_HR+12) + ":" + String.format("%02d", END_MIN) + "AM");
                    }
                    else {
                        startTime.setText(" Time: " + String.format("%02d", START_HR+12) + ":" + String.format("%02d", START_MIN) + "AM - " +
                                String.format("%02d", END_HR) + ":" + String.format("%02d", END_MIN) + "AM");
                    }
                }

                else if(START_HR > 12) {
                    if(END_HR == 12) {
                        startTime.setText(" Time: " + String.format("%02d", START_HR-12) + ":" + String.format("%02d", START_MIN) + "PM - " +
                                String.format("%02d", END_HR) + ":" + String.format("%02d", END_MIN) + "PM");
                    }
                    else if(END_HR > 12) {
                        startTime.setText(" Time: " + String.format("%02d", START_HR-12) + ":" + String.format("%02d", START_MIN) + "PM - " +
                                String.format("%02d", END_HR-12) + ":" + String.format("%02d", END_MIN) + "PM");
                    }
                    else if (END_HR == 00) {
                        startTime.setText(" Time: " + String.format("%02d", START_HR-12) + ":" + String.format("%02d", START_MIN) + "PM - " +
                                String.format("%02d", END_HR+12) + ":" + String.format("%02d", END_MIN) + "AM");
                    }
                    else {
                        startTime.setText(" Time: " + String.format("%02d", START_HR-12) + ":" + String.format("%02d", START_MIN) + "PM - " +
                                String.format("%02d", END_HR) + ":" + String.format("%02d", END_MIN) + "AM");
                    }
                }

                else {
                    if(END_HR == 12) {
                        startTime.setText(" Time: " + String.format("%02d", START_HR) + ":" + String.format("%02d", START_MIN) + "AM - " +
                                String.format("%02d", END_HR) + ":" + String.format("%02d", END_MIN) + "PM");
                    }
                    else if(END_HR > 12) {
                        startTime.setText(" Time: " + String.format("%02d", START_HR) + ":" + String.format("%02d", START_MIN) + "AM - " +
                                String.format("%02d",END_HR-12) + ":" + String.format("%02d", END_MIN) + "PM");
                    }
                    else if (END_HR == 00) {
                        startTime.setText(" Time: " + String.format("%02d", START_HR) + ":" + String.format("%02d", START_MIN) + "PM - " +
                                String.format("%02d", END_HR+12) + ":" + String.format("%02d", END_MIN) + "AM");
                    }
                    else {
                        startTime.setText(" Time: " + String.format("%02d", START_HR) + ":" + String.format("%02d", START_MIN) + "AM - " +
                                String.format("%02d", END_HR) + ":" + String.format("%02d", END_MIN) + "AM");
                    }
                }

            }
        }

        disableFields();
    }


    // Updates event with new information
    public void updateEvent(View view) {
        eventDbHelper = new EventDbHelper(getApplicationContext());
        sqLiteDatabase = eventDbHelper.getReadableDatabase();

        // Get new event details
        // old_event_name, new_date, new_month, new_year, new_name, new_location, new_start_hr,
        // new_start_min, new_end_hr, new_end_min, new_description, new_participants
        String new_name, new_location, new_description, new_participants, new_type;
        int new_date, new_month, new_year, new_startHr, new_startMin, new_endHr, new_endMin;
        new_name = display_Name.getText().toString();
        new_location = display_Location.getText().toString();
        new_description = display_Description.getText().toString();
        new_participants = display_Participants.getText().toString();
        new_date = datePicker.getDayOfMonth();
        new_month = datePicker.getMonth()+1;
        new_year = datePicker.getYear();
        new_type = drop_eventType.getSelectedItem().toString();

        if (check.isChecked() == true) { //all day event
            new_startHr = -1;
            new_startMin = -1;
            new_endHr = -1;
            new_endMin = -1;
        }
        else {
            new_startHr = startPicker.getHour();
            new_startMin = startPicker.getMinute();
            new_endHr = endPicker.getHour();
            new_endMin = endPicker.getMinute();
        }

        eventDbHelper.updateInfo(name, new_date, new_month, new_year, new_name, new_location, new_startHr,
                new_startMin, new_endHr, new_endMin, new_description, new_participants, new_type, sqLiteDatabase);

        eventDbHelper.close();
        Toast.makeText(this, "Event updated.", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, null);
        Intent intent = getIntent();
        intent.putExtra("name", new_name);
        finish();
        startActivity(intent);
    }


    // Deletes event
    public void deleteEvent(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Delete Event")
                .setMessage("Are you sure you want to delete this event?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eventDbHelper = new EventDbHelper(getApplicationContext());
                        sqLiteDatabase = eventDbHelper.getReadableDatabase();
                        eventDbHelper.deleteInfo(name, sqLiteDatabase);
                        eventDbHelper.close();
                        Toast.makeText(getApplicationContext(), "Event deleted.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        setResult(RESULT_OK, null);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }


    // Enables text fields for editing
    public void editEvent(View view) {
        display_Name.setEnabled(true);
        display_Location.setEnabled(true);
        display_Description.setEnabled(true);
        display_Participants.setEnabled(true);
        drop_eventType.setVisibility(View.VISIBLE);
        datePicker.setVisibility(View.VISIBLE);
        allDayTxt.setVisibility(View.VISIBLE);
        check.setVisibility(View.VISIBLE);
        endTime.setVisibility(View.VISIBLE);
        startPicker.setVisibility(View.VISIBLE);
        endPicker.setVisibility(View.VISIBLE);
        button_save.setVisibility(View.VISIBLE);
        button_delete.setVisibility(View.VISIBLE);
        eventType.setVisibility(View.GONE);
        date.setVisibility(View.GONE);
        button_edit.setVisibility(View.GONE);
        button_share.setVisibility(View.GONE);
        startTime.setText(" Start Time");
        title.setText("Edit Event");
        display_Description.setHint("Description");
        display_Participants.setHint("Enter participants email separated by a comma");
        display_Location.setHint("Location");
    }


    // Disables text fields for editing
    public void disableFields() {
        // Disable edit fields
        display_Name.setEnabled(false);
        display_Location.setEnabled(false);
        display_Description.setEnabled(false);
        display_Participants.setEnabled(false);
        eventType.setVisibility(View.VISIBLE);
        date.setVisibility(View.VISIBLE);
        drop_eventType.setVisibility(View.GONE);
        datePicker.setVisibility(View.GONE);
        allDayTxt.setVisibility(View.GONE);
        check.setVisibility(View.GONE);
        endTime.setVisibility(View.GONE);
        startPicker.setVisibility(View.GONE);
        endPicker.setVisibility(View.GONE);
        button_save.setVisibility(View.GONE);
        button_delete.setVisibility(View.GONE);
        display_Description.setHint("No Description");
        display_Participants.setHint("No Participants");
        display_Location.setHint("No Location");
        title.setText("Event Details");
    }


    // Share event
    public void shareEvent(View view) {
        Cursor cursor;
        int DATE, MONTH, YEAR, START_HR, START_MIN, END_HR, END_MIN;
        String LOCATION, DESCRIPTION, PARTICIPANTS;
        String subject = "subject";
        String mail_body = "mail body";
        String participants = "email_address";


        eventDbHelper = new EventDbHelper(getApplicationContext());
        sqLiteDatabase = eventDbHelper.getReadableDatabase();
        cursor = eventDbHelper.getEvent(name, sqLiteDatabase);
        if (cursor.moveToFirst()) {
            DATE = cursor.getInt(0);
            MONTH = cursor.getInt(1);
            YEAR = cursor.getInt(2);
            LOCATION = cursor.getString(3);
            START_HR = cursor.getInt(4);
            START_MIN = cursor.getInt(5);
            END_HR = cursor.getInt(6);
            END_MIN = cursor.getInt(7);
            DESCRIPTION = cursor.getString(8);
            PARTICIPANTS = cursor.getString(9);

            participants = PARTICIPANTS;
            subject = new String("Event Reminder: " + name);
            if (START_HR == -1) {
                mail_body = new String("You have an event:\n" +
                        "\nEvent name: " + name +
                        "\nEvent date: " + String.format("%02d", MONTH) + "/" + String.format("%02d", DATE) + "/" + YEAR +
                        "\nEvent time: All-Day" +
                        "\nLocation: " + LOCATION +
                        "\nDescription: " + DESCRIPTION);
            }
            else {
                mail_body = new String("You have an event:\n" +
                        "\nEvent name: " + name +
                        "\nEvent date: " + String.format("%02d", MONTH) + "/" + String.format("%02d", DATE) + "/" + YEAR +
                        "\nEvent time: " + String.format("%02d", START_HR) + ":" + String.format("%02d", START_MIN) + " - " + String.format("%02d", END_HR) + ":" + String.format("%02d", END_MIN) +
                        "\nLocation: " + LOCATION +
                        "\nDescription: " + DESCRIPTION);
            }
        }

        Intent intent = new Intent(this, ShareEvent.class);
        intent.putExtra("participants", participants);
        intent.putExtra("subject", subject);
        intent.putExtra("mail_body", mail_body);
        startActivity(intent);
        setResult(RESULT_OK, null);
        finish();
    }


    @Override
    public void onClick(View view) {
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
    }
}

