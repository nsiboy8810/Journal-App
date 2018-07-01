package com.example.android.journalapp.Data;

import android.provider.BaseColumns;

public final class NoteContract {
    public static final class NoteEntry implements BaseColumns{
        public final static  String TABLE_NAME = "notes";
        public final static String _ID = "_1d";
        public final static String COLUMN_BODY = "body";
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_EMAIL = "email";
        public final static String COLUMN_MONTH = "month";
        public final static String COLUMN_MONTH_DAY = "day";
        public final static String COLUMN_YEAR = "year";


    }
}
