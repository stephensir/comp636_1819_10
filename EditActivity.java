package com.stephensir.notedemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class EditActivity extends AppCompatActivity {

    // Properties
    private String TAG = "EditActivity===>";
    private String noteId;
    private EditText noteDesc;
    private Button btnEdit, btnDelete;
    private DBHelper dbhelper = new DBHelper(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Check Android Version
        final String APP_NAME = getResources().getString(R.string.app_name);
        final String APP_VERSION_CODE = String.valueOf(BuildConfig.VERSION_CODE);
        final String APP_VERSION_NAME = BuildConfig.VERSION_NAME;
        final int SDK_VERSION = Build.VERSION.SDK_INT;
        Log.d(TAG,"SDK_INT= "+ SDK_VERSION);

        // Toolbar setup
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.DKGRAY);
        toolbar.setTitleTextColor(Color.YELLOW);
        toolbar.setTitle("Edit/Delete");
        toolbar.setSubtitleTextColor(Color.RED);
        toolbar.setSubtitle(APP_VERSION_NAME);
        setSupportActionBar(toolbar);

        Log.d(TAG,"onCreate()");

        noteDesc = findViewById(R.id.noteDesc);

        // GetExtra
        Intent objIntent = getIntent();
        noteId = objIntent.getStringExtra("noteId");

        // Get notedesc by noteId
        Log.d(TAG, "onCreate()->getNoteDescByNoteId");
        HashMap<String, String> noteList = dbhelper.getNoteById(noteId);

        Log.d(TAG,"onCreate()->noteList->"+noteList.get("noteDesc"));

        if(noteList.size()!=0) {
            Log.d(TAG,"onCreate()->update noteDesc");
            noteDesc.setText(noteList.get("noteDesc"));
        }

        // Edit Button
        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doEdit();
            }
        });

        // Delete Button
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doDelete();
            }
        });

    } //onCreate()

    private void doEdit(){
        Log.d(TAG,"doEdit()");
        HashMap<String, String> queryValues =  new  HashMap<String, String>();
        queryValues.put("noteId", noteId);
        queryValues.put("noteDesc", noteDesc.getText().toString());
        dbhelper.updateNote(queryValues);
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    private void doDelete(){
        Log.d(TAG,"doDelete()");
        dbhelper.deleteNote(noteId);
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

}

