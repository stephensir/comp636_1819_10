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

public class NewActivity extends AppCompatActivity {

    // Properties
    private String TAG = "NewActivity===>";
    private EditText noteDesc;
    private Button btnAdd;
    private DBHelper dbhelper = new DBHelper(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

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
        toolbar.setTitle("New");
        toolbar.setSubtitleTextColor(Color.RED);
        toolbar.setSubtitle(APP_VERSION_NAME);
        setSupportActionBar(toolbar);

        Log.d(TAG,"onCreate()");

        // references
        noteDesc = findViewById(R.id.noteDesc);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onCreate()->btnAdd->onClick()");
                HashMap<String, String> queryValues =  new  HashMap<String, String>();
                queryValues.put("noteDesc", noteDesc.getText().toString());
                dbhelper.addNote(queryValues);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                NewActivity.this.finish();
            }
        });
    } //onCreate()

}

