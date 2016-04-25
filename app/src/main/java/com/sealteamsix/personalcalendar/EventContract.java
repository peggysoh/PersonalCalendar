package com.sealteamsix.personalcalendar;

/**
 * Created by SealTeam6 on 4/1/2016.
 *
 * Database column names
 */
public class EventContract {

    public static abstract class NewEventInfo {

        // Column names
        public static final String EVENT_NAME = "event_name";
        public static final String LOCATION = "location";
        public static final String DESCRIPTION = "description";
        public static final String PARTICIPANTS = "participants";
        public static final String START_HR = "start_hour";
        public static final String START_MIN = "start_min";
        public static final String END_HR = "end_time";
        public static final String END_MIN = "end_min";
        public static final String DATE = "date";
        public static final String MONTH = "month";
        public static final String YEAR = "year";
        public static final String TABLE_NAME = "event_info";

    }

}
