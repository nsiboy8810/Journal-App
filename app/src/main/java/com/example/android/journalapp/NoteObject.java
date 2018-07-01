package com.example.android.journalapp;

public class NoteObject {
    String mBody, mEail, mTitle, day, month, year;
    int id;
    public NoteObject(int id, String email, String title, String body, String day, String month, String year){
        mBody = body;
        mEail = email;
        mTitle = title;
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
    }


    public String getmBody() {
        return mBody;
    }
    public int getId(){return id;}

    public String getmEail() {
        return mEail;
    }

    public String getmTitle() {
        return mTitle;
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

