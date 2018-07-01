package com.example.android.journalapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.journalapp.Data.NoteContract.NoteEntry;


public class NoteReaderDbHelper extends SQLiteOpenHelper {
    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME ="notes.db";
    public final static String TEXT = "TEXT";
    public final static String INTEGER = "INTEGER";
    public final static String SQL_DELETE_ENTRIES = "DROP TABLE" + NoteEntry.TABLE_NAME;
    public final static String SQL_CREATE_ENTRIES = "CREATE TABLE "+ NoteEntry.TABLE_NAME + "("+NoteEntry._ID +" "+ INTEGER + " PRIMARY KEY AUTOINCREMENT, "+ NoteEntry.COLUMN_EMAIL +" "+ TEXT +" NOT NULL, "+ NoteEntry.COLUMN_TITLE +" "+ TEXT +", "+ NoteEntry.COLUMN_BODY +" "+ TEXT +" NOT NULL, "+NoteEntry.COLUMN_MONTH_DAY +" "+TEXT +" NOT NULL, "+ NoteEntry.COLUMN_MONTH +" "+ TEXT +" NOT NULL, "+NoteEntry.COLUMN_YEAR +" "+TEXT +" NOT NULL)";
    public NoteReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
//        onCreate(sqLiteDatabase);


    }
}
