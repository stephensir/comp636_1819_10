package com.stephensir.notedemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // Properties
    private String TAG = "MainActivity===>";
    private TextView noteId;
    private DBHelper dbhelper = new DBHelper(this);
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"onCreate()");

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
        toolbar.setTitle(APP_NAME);
        toolbar.setSubtitleTextColor(Color.RED);
        toolbar.setSubtitle(APP_VERSION_NAME);
        setSupportActionBar(toolbar);

        // Button Add
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NewActivity.class));
            }
        });

        // Create List
        ArrayList<HashMap<String, String>> noteList =  dbhelper.getAllNotes();

        // Construct listView
        if(noteList.size()!=0) {
            listView = findViewById(R.id.listView);
            // Set onclick listener
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    noteId = view.findViewById(R.id.noteId);
                    String Id = noteId.getText().toString();
                    Intent intent = new Intent(getApplicationContext(),EditActivity.class);
                    intent.putExtra("noteId", Id);
                    startActivity(intent);
                }
            });
            ListAdapter adapter = new SimpleAdapter(MainActivity.this,
                    noteList,
                    R.layout.note_row,
                    new String[] { "noteId","noteDesc"},
                    new int[] {R.id.noteId, R.id.noteDesc}
            );
            listView.setAdapter(adapter);
        }
    }

}

