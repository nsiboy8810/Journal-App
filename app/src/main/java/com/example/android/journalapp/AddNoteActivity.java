package com.example.android.journalapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.android.journalapp.Data.NoteReaderDbHelper;
import com.example.android.journalapp.Data.NoteContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddNoteActivity extends AppCompatActivity {
    EditText mEditNoteBoby, mEditNoteTitle;
    String mNoteBody, mNoteTitle, mNoteEmail;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_item_save){
            insertNote();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void insertNote(){
        FirebaseUser user = mAuth.getCurrentUser();
        Time time = new Time(Time.getCurrentTimezone());
        time.setToNow();
        mEditNoteBoby = findViewById(R.id.et_note_body);
        mEditNoteTitle = findViewById(R.id.et_note_title);
        mNoteBody = mEditNoteBoby.getText().toString();
        mNoteTitle = mEditNoteTitle.getText().toString();
        mNoteEmail = user != null ? user.getEmail() : null;

        String day = String.valueOf(time.monthDay), month = String.valueOf(time.month), year=String.valueOf(time.year);

        NoteReaderDbHelper fHelper = new NoteReaderDbHelper(this);
        SQLiteDatabase db = fHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NoteContract.NoteEntry.COLUMN_EMAIL, mNoteEmail);
        values.put(NoteContract.NoteEntry.COLUMN_TITLE, mNoteTitle);
        values.put(NoteContract.NoteEntry.COLUMN_BODY, mNoteBody);
        values.put(NoteContract.NoteEntry.COLUMN_MONTH_DAY, day);
        values.put(NoteContract.NoteEntry.COLUMN_MONTH, month);
        values.put(NoteContract.NoteEntry.COLUMN_YEAR, year);

        db.insert(NoteContract.NoteEntry.TABLE_NAME,null,values);

    }
}
