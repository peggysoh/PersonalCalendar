package com.sealteamsix.personalcalendar;

import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * Created by SealTeam6 on 4/11/2016.
 *
 * Delete Event Controller
 * Deletes event from database
 */
public class DeleteEvent {
    private EventDbHelper eventDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private DatePicker datePicker;
    private int date, month, year;
    String name;

    public void deleteEvent(View view) {
        eventDbHelper = new EventDbHelper(view.getContext());
        sqLiteDatabase = eventDbHelper.getReadableDatabase();
        eventDbHelper.deleteInfo(name, sqLiteDatabase);
        Toast.makeText(view.getContext(), "Event deleted.", Toast.LENGTH_SHORT).show();
        eventDbHelper.close();
    }
}
