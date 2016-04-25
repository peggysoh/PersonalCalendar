package com.sealteamsix.personalcalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by SealTeam6 on 4/1/2016.
 *
 * Database Controller to add, update or remove information from database.
 */
public class EventDbHelper extends SQLiteOpenHelper {
    // Creates new database or opens existing database
    private static final String DATABASE_NAME = "EVENTINFO.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY =
            "CREATE TABLE " + EventContract.NewEventInfo.TABLE_NAME + "(" +
                    "id" + " INTEGER PRIMARY KEY," +
                    EventContract.NewEventInfo.DATE + " INTEGER," +
                    EventContract.NewEventInfo.MONTH + " INTEGER," +
                    EventContract.NewEventInfo.YEAR + " INTEGER," +
                    EventContract.NewEventInfo.EVENT_NAME + " TEXT," +
                    EventContract.NewEventInfo.LOCATION + " TEXT," +
                    EventContract.NewEventInfo.START_HR  + " INTEGER," +
                    EventContract.NewEventInfo.START_MIN + " INTEGER," +
                    EventContract.NewEventInfo.END_HR + " INTEGER," +
                    EventContract.NewEventInfo.END_MIN + " INTEGER," +
                    EventContract.NewEventInfo.DESCRIPTION + " TEXT," +
                    EventContract.NewEventInfo.PARTICIPANTS + " TEXT);";

    public EventDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DATABASE OPERATIONS", "Database created / opened...");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // If DB is created for first time
        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS", "Table created...");
    }


    // Add event to database
    public void addInfo(int date, int month, int year, String name, String location, int start_hr,
                        int start_min, int end_hr, int end_min, String description,
                        String participants, SQLiteDatabase db) {
        // Set values
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventContract.NewEventInfo.DATE, date);
        contentValues.put(EventContract.NewEventInfo.MONTH, month);
        contentValues.put(EventContract.NewEventInfo.YEAR, year);
        contentValues.put(EventContract.NewEventInfo.EVENT_NAME, name);
        contentValues.put(EventContract.NewEventInfo.LOCATION, location);
        contentValues.put(EventContract.NewEventInfo.START_HR, start_hr);
        contentValues.put(EventContract.NewEventInfo.START_MIN, start_min);
        contentValues.put(EventContract.NewEventInfo.END_HR, end_hr);
        contentValues.put(EventContract.NewEventInfo.END_MIN, end_min);
        contentValues.put(EventContract.NewEventInfo.DESCRIPTION, description);
        contentValues.put(EventContract.NewEventInfo.PARTICIPANTS, participants);

        // Insert into DB
        db.insert(EventContract.NewEventInfo.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "One row inserted...");
    }


    // Gets all events from database
    public Cursor getInfo(SQLiteDatabase db) {
        Cursor cursor;
        String[] projections = {EventContract.NewEventInfo.DATE, EventContract.NewEventInfo.MONTH,
                EventContract.NewEventInfo.YEAR, EventContract.NewEventInfo.EVENT_NAME,
                EventContract.NewEventInfo.LOCATION, EventContract.NewEventInfo.START_HR,
                EventContract.NewEventInfo.START_MIN, EventContract.NewEventInfo.END_HR,
                EventContract.NewEventInfo.END_MIN, EventContract.NewEventInfo. DESCRIPTION,
                EventContract.NewEventInfo.PARTICIPANTS};

        cursor = db.query(EventContract.NewEventInfo.TABLE_NAME, projections, null, null, null, null,
                EventContract.NewEventInfo.YEAR + " ASC," + EventContract.NewEventInfo.MONTH + " ASC," + EventContract.NewEventInfo.DATE + " ASC");

        return cursor;
    }


    // Gets specific event from database
    public Cursor getEvent(String event_name, SQLiteDatabase sqLiteDatabase) {
        String[] projections = {EventContract.NewEventInfo.DATE, EventContract.NewEventInfo.MONTH,
                EventContract.NewEventInfo.YEAR, EventContract.NewEventInfo.LOCATION,
                EventContract.NewEventInfo.START_HR, EventContract.NewEventInfo.START_MIN,
                EventContract.NewEventInfo.END_HR, EventContract.NewEventInfo.END_MIN,
                EventContract.NewEventInfo.DESCRIPTION, EventContract.NewEventInfo.PARTICIPANTS,
                EventContract.NewEventInfo.EVENT_NAME};
        String selection = EventContract.NewEventInfo.EVENT_NAME + " LIKE ?";
        String[] selection_args = {event_name};

        Cursor cursor = sqLiteDatabase.query(EventContract.NewEventInfo.TABLE_NAME, projections, selection, selection_args, null, null, null);

        return cursor;
    }


    // Deletes event from database
    public void deleteInfo(String event_name, SQLiteDatabase sqLiteDatabase) {

        String selection = EventContract.NewEventInfo.EVENT_NAME + " LIKE ?";
        String[] selection_args = {event_name};

        sqLiteDatabase.delete(EventContract.NewEventInfo.TABLE_NAME, selection, selection_args);
    }


    // Updates event in database
    public int updateInfo(String old_event_name, int new_date, int new_month, int new_year, String new_name, String new_location, int new_start_hr,
                           int new_start_min, int new_end_hr, int new_end_min, String new_description, String new_participants, SQLiteDatabase sqLiteDatabase) {
        // Set values
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventContract.NewEventInfo.DATE, new_date);
        contentValues.put(EventContract.NewEventInfo.MONTH, new_month);
        contentValues.put(EventContract.NewEventInfo.YEAR, new_year);
        contentValues.put(EventContract.NewEventInfo.EVENT_NAME, new_name);
        contentValues.put(EventContract.NewEventInfo.LOCATION, new_location);
        contentValues.put(EventContract.NewEventInfo.START_HR, new_start_hr);
        contentValues.put(EventContract.NewEventInfo.START_MIN, new_start_min);
        contentValues.put(EventContract.NewEventInfo.END_HR, new_end_hr);
        contentValues.put(EventContract.NewEventInfo.END_MIN, new_end_min);
        contentValues.put(EventContract.NewEventInfo.DESCRIPTION, new_description);
        contentValues.put(EventContract.NewEventInfo.PARTICIPANTS, new_participants);

        String selection = EventContract.NewEventInfo.EVENT_NAME + " LIKE ?";
        String[] selection_args = {old_event_name};

        int count = sqLiteDatabase.update(EventContract.NewEventInfo.TABLE_NAME, contentValues, selection, selection_args);
        return count;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
