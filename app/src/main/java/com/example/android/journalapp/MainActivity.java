package com.example.android.journalapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.journalapp.Data.NoteReaderDbHelper;
import com.example.android.journalapp.Data.NoteContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NoteAdapter.ListItemOnClickListener{


    RecyclerView recyclerView;
    TextView mNameTextview, mEmailTextView;
    CircleImageView cImage;
    FloatingActionButton mCreateNewNote;
    NoteAdapter noteAdapter;
    List<NoteObject> mNoteList = new ArrayList<>();
    Time time;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNameTextview = findViewById(R.id.tv_profile_name);
        mEmailTextView = findViewById(R.id.tv_profile_email);
        cImage = findViewById(R.id.profile_image);
        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null){
            finish();
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }
        FirebaseUser user = mAuth.getCurrentUser();
        Glide.with(this).load(user.getPhotoUrl()).into(cImage);
        mNameTextview.setText(user.getDisplayName());
        mEmailTextView.setText(user.getEmail());


        time = new Time(Time.getCurrentTimezone());
        time.setToNow();

        mCreateNewNote = findViewById(R.id.btn_create_new_note);
        recyclerView = (RecyclerView) findViewById(R.id.rv_list_notes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        noteAdapter = new NoteAdapter(this);
        displayDatabaseIfo();
        recyclerView.setAdapter(noteAdapter);
    }

    public void displayDatabaseIfo(){
        FirebaseUser user = mAuth.getCurrentUser();

            String emailArg = user.getEmail();

        mNoteList.clear();
        noteAdapter.noteArray.clear();
        noteAdapter = new NoteAdapter(this);
        NoteReaderDbHelper noteReaderDbHelper = new NoteReaderDbHelper(this);
        SQLiteDatabase db = noteReaderDbHelper.getReadableDatabase();
        String [] projector = {
                NoteContract.NoteEntry._ID,
                NoteContract.NoteEntry.COLUMN_EMAIL,
                NoteContract.NoteEntry.COLUMN_TITLE,
                NoteContract.NoteEntry.COLUMN_BODY,
                NoteContract.NoteEntry.COLUMN_MONTH_DAY,
                NoteContract.NoteEntry.COLUMN_MONTH,
                NoteContract.NoteEntry.COLUMN_YEAR
        };
        String selectionn = NoteContract.NoteEntry.COLUMN_EMAIL +"=?";
        String [] selectArg = {emailArg};
        Cursor cursor =db.query(NoteContract.NoteEntry.TABLE_NAME, projector,selectionn,selectArg,null,null,null);
        try {
        while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String email = cursor.getColumnName(1);
                String title = cursor.getColumnName(2);
                String body = cursor.getString(3);
                String monthDay = cursor.getString(4);
                String month = cursor.getString(5);
                String year = cursor.getString(6);

                mNoteList.add(new NoteObject(id, email, title, body,monthDay,month,year));
            }
        } finally {
            cursor.close();
        }
        for (int i = mNoteList.size(); i>0; i--){
            noteAdapter.noteArray.add(mNoteList.get(i-1));

        }
    }
    public void CreateNewNote(View view){
        Intent createNewNoteIntent = new Intent(this, AddNoteActivity.class);
        startActivity(createNewNoteIntent);
    }
    @Override
    public void onListItemClickListener(int clickedIndex) {
        Intent intent = new Intent(this, viewThoughtActivity.class);
        intent.putExtra("text_body", noteAdapter.noteArray.get(clickedIndex).mBody);
        intent.putExtra("text_title", noteAdapter.noteArray.get(clickedIndex).mTitle);
        String myId = String.valueOf(noteAdapter.noteArray.get(clickedIndex).id);
        intent.putExtra("id", myId);
        startActivity(intent);
    }
}
