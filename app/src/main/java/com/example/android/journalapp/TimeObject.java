package com.example.android.journalapp;

public class TimeObject {
    String day, month, year;
    public TimeObject(String day, String month, String year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }
}
