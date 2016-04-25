package com.sealteamsix.personalcalendar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class SearchEvent extends AppCompatActivity {

    private ListView listView;
    private EditText searchName;
    String search_name, last_search;
    ListDataAdapter listDataAdapter;
    SQLiteDatabase sqLiteDatabase;
    EventDbHelper eventDbHelper;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        searchName = (EditText) findViewById(R.id.search_box);
        searchName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            searchEvent(v);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        listDataAdapter = new ListDataAdapter(this, R.layout.row_layout);
        listView.setAdapter(listDataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = (((TextView) view.findViewById(R.id.text_event_name)).getText()).toString();
                editEvent(view, selectedName);
                finish();
            }
        });
    }


    public void searchEvent(View view) {
        Cursor cursor;
        int DATE, MONTH, YEAR, START_HR, START_MIN, END_HR, END_MIN;
        String LOCATION, DESCRIPTION, PARTICIPANTS, NAME;

        listDataAdapter = new ListDataAdapter(this, R.layout.row_layout);
        listView.setAdapter(listDataAdapter);

        search_name = searchName.getText().toString();
        last_search = search_name;
        eventDbHelper = new EventDbHelper(getApplicationContext());
        sqLiteDatabase = eventDbHelper.getReadableDatabase();
        cursor = eventDbHelper.searchEvent(search_name, sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {
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
                NAME = cursor.getString(10);

                DataProvider dataProvider = new DataProvider(DATE, MONTH, YEAR, NAME, LOCATION,
                        START_HR, START_MIN, END_HR, END_MIN, DESCRIPTION, PARTICIPANTS);
                listDataAdapter.add(dataProvider);
            } while (cursor.moveToNext()); // true if there is rows after
        }
    }


    // User clicked on an event
    public void editEvent (View view, String selectedName) {
        Intent intent = new Intent(this, EditEvent.class);
        intent.putExtra("name", selectedName);
        startActivityForResult(intent, 1);
        setResult(RESULT_OK, null);
        finish();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SearchEvent Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
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
                "SearchEvent Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.sealteamsix.personalcalendar/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
