
package com.example.android.journalapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.android.journalapp.Data.NoteReaderDbHelper;
import com.example.android.journalapp.Data.NoteContract;

public class EditNoteActivity extends AppCompatActivity {
    EditText mBodyEditText, mNoteEditText;
    String mNoteBody,mNoteTitle, bodyGotten, titleGotten, gottenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        mBodyEditText = findViewById(R.id.et_edit_note_body);
        mNoteEditText = findViewById(R.id.et_edit_note_title);
        Intent intent = getIntent();
        bodyGotten = intent.getStringExtra("noteBody");
        titleGotten = intent.getStringExtra("noteTitle");
        gottenId = intent.getStringExtra("id");
        mNoteEditText.setText(titleGotten);
        mBodyEditText.setText(bodyGotten);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_item_save_note){
            updateDb();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    void updateDb(){
        Time time = new Time(Time.getCurrentTimezone());
        time.setToNow();
        String day = String.valueOf(time.monthDay), month = String.valueOf(time.month), year=String.valueOf(time.year);
        mBodyEditText = findViewById(R.id.et_edit_note_body);
        mNoteEditText = findViewById(R.id.et_edit_note_title);
        mNoteTitle = mNoteEditText.getText().toString();
        mNoteBody = mBodyEditText.getText().toString();
        String selection = NoteContract.NoteEntry._ID + "=?";
        String selectionArgs[] = {String.valueOf(gottenId)};

        NoteReaderDbHelper fHelper = new NoteReaderDbHelper(this);
        SQLiteDatabase db = fHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NoteContract.NoteEntry.COLUMN_TITLE, mNoteTitle);
        values.put(NoteContract.NoteEntry.COLUMN_BODY, mNoteBody);
        values.put(NoteContract.NoteEntry.COLUMN_MONTH_DAY, day);
        values.put(NoteContract.NoteEntry.COLUMN_MONTH, month);
        values.put(NoteContract.NoteEntry.COLUMN_YEAR, year);
        db.update(NoteContract.NoteEntry.TABLE_NAME,values, selection, selectionArgs);

    }
}
