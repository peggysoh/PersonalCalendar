package com.sealteamsix.personalcalendar;

/**
 * Created by SealTeam6 on 4/1/2016.
 *
 * Gets & Sets for event information stored in database.
 */
public class DataProvider {

    private int id; // use to properly identify event
    private int date;
    private int month;
    private int year;
    private int start_hr;
    private int start_min;
    private int end_hr;
    private int end_min;
    private String name;
    private String location;
    private String description;
    private String participants;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getStart_hr() {
        return start_hr;
    }

    public void setStart_hr(int start_hr) {
        this.start_hr = start_hr;
    }

    public int getStart_min() {
        return start_min;
    }

    public void setStart_min(int start_min) {
        this.start_min = start_min;
    }

    public int getEnd_hr() {
        return end_hr;
    }

    public void setEnd_hr(int end_hr) {
        this.end_hr = end_hr;
    }

    public int getEnd_min() {
        return end_min;
    }

    public void setEnd_min(int end_min) {
        this.end_min = end_min;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public DataProvider(int date, int month, int year, String name, String location, int start_hr,
                        int start_min, int end_hr, int end_min, String description, String participants, String type) {

        this.date = date;
        this.month = month;
        this.year = year;
        this.name = name;
        this.location = location;
        this.start_hr = start_hr;
        this.start_min = start_min;
        this.end_hr = end_hr;
        this.end_min = end_min;
        this.description = description;
        this.participants = participants;
        this.type = type;
    }
}
