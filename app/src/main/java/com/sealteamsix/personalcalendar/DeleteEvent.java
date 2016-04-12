package com.sealteamsix.personalcalendar;

import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;

/**
 * Created by never on 4/11/2016.
 */
public class DeleteEvent {
    // Delete Event Controller
    private EventDbHelper eventDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private DatePicker datePicker;
    private int date, month, year;
    String name;

    public void deleteEvent(View view) {
        sqLiteDatabase = eventDbHelper.getReadableDatabase();
        eventDbHelper.deleteInfo(name, sqLiteDatabase);
        eventDbHelper.close();

        //setResult(RESULT_OK, null);
        //finish();
    }
}
