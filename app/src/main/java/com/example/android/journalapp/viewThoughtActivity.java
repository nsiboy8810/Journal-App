package com.example.android.journalapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.journalapp.Data.NoteReaderDbHelper;
import com.example.android.journalapp.Data.NoteContract;

public class viewThoughtActivity extends AppCompatActivity {

    String body, id, title;
    TextView tv, tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_thought);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        displayDatabaseIfo(id);
        tv = findViewById(R.id.tv_view_body);
        tvTitle = findViewById(R.id.tv_view_title);
        tvTitle.setText(title);
        tv.setText(body);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_item_edit){
            Intent intent = new Intent(this, EditNoteActivity.class);
            intent.putExtra("noteBody", body);
            intent.putExtra("noteTitle", title);
            intent.putExtra("id", id);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void displayDatabaseIfo(String id){

        NoteReaderDbHelper noteReaderDbHelper = new NoteReaderDbHelper(this);
        SQLiteDatabase db = noteReaderDbHelper.getReadableDatabase();
        String [] projector = {NoteContract.NoteEntry._ID, NoteContract.NoteEntry.COLUMN_TITLE, NoteContract.NoteEntry.COLUMN_BODY};
        String selection = NoteContract.NoteEntry._ID + "=?";
        String[] selectionArgs = {id};
        Cursor cursor =db.query(NoteContract.NoteEntry.TABLE_NAME, projector,selection,selectionArgs,null,null,null);
        cursor.moveToNext();
        try {
            body = cursor.getString(2);
            title= cursor.getString(1);
        }finally {
            cursor.close();
        }



    }
}
