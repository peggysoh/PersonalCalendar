package com.sealteamsix.personalcalendar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private int cvMonth, cvDate, cvYear;
    private ListView listView;
    int todayDate, todayMonth, todayYear;
    SQLiteDatabase sqLiteDatabase;
    EventDbHelper eventDbHelper;
    Cursor cursor;
    ListDataAdapter listDataAdapter;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add);

        // Set current date
        Calendar c = Calendar.getInstance();
        cvDate = c.get(Calendar.DATE);
        cvMonth = c.get(Calendar.MONTH) + 1;
        cvYear = c.get(Calendar.YEAR);
        todayDate = c.get(Calendar.DATE);
        todayMonth = c.get(Calendar.MONTH) + 1;
        todayYear = c.get(Calendar.YEAR);

        // Set new selected date
        CalendarView cv =  (CalendarView) findViewById(R.id.calendarView);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int date) {
                cvDate = date;
                cvMonth = month+1;
                cvYear = year;
                initializeList();
            }
        });

        // Initialize list
        initializeList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = (((TextView) view.findViewById(R.id.text_event_name)).getText()).toString();
                Toast.makeText(getApplicationContext(), selectedName + " is selected.", Toast.LENGTH_SHORT).show();
                editEvent(view, selectedName);
            }
        });
    }


    // Update list to show events for selected date
    public void initializeList () {
        // Initializing list
        listView = (ListView) findViewById(R.id.listView);
        listDataAdapter = new ListDataAdapter(this, R.layout.row_layout);
        listView.setAdapter(listDataAdapter);
        eventDbHelper = new EventDbHelper(getApplicationContext());
        sqLiteDatabase = eventDbHelper.getReadableDatabase();
        cursor = eventDbHelper.getInfo(sqLiteDatabase);
        if(cursor.moveToFirst()) {  // true if there is info in DB
            do {
                int date, month, year, start_hr, start_min, end_hr, end_min;
                String name, location, description, participants;

                // Get data from cursor
                // 0 DATE 1 MONTH 2 YEAR 3 EVENT_NAME 4 LOCATION 5 START_HR 6 START_MIN
                // 7 END_HR 8 END_MIN 9 DESCRIPTION 10 PARTICIPANTS
                date = cursor.getInt(0);
                month = cursor.getInt(1);
                year = cursor.getInt(2);
                name = cursor.getString(3);
                location = cursor.getString(4);
                start_hr = cursor.getInt(5);
                start_min = cursor.getInt(6);
                end_hr = cursor.getInt(7);
                end_min = cursor.getInt(8);
                description = cursor.getString(9);
                participants = cursor.getString(10);
                DataProvider dataProvider = new DataProvider(date, month, year, name, location,
                        start_hr, start_min, end_hr, end_min, description, participants);
                if (year > cvYear) {
                    listDataAdapter.add(dataProvider);
                }
                else if (year == cvYear) {
                    if (month > cvMonth) {
                        listDataAdapter.add(dataProvider);
                    }
                    else if (month == cvMonth) {
                        if (date >= cvDate) {
                            listDataAdapter.add(dataProvider);
                        }
                    }
                }
            } while (cursor.moveToNext()); // true if there is rows after
        }
    }


    // User clicked on an event
    public void editEvent (View view, String selectedName) {
        Intent intent = new Intent(this, EditEvent.class);
        intent.putExtra("name", selectedName);
        startActivityForResult(intent, 1);
    }


    // User clicked add event
    public void addEvent(View view) {
        Intent intent = new Intent(this, AddEvent.class);
        intent.putExtra("date", cvDate);
        intent.putExtra("month", cvMonth);
        intent.putExtra("year", cvYear);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
            this.finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
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
                "Main Page", // TODO: Define a title for the content shown.
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
