package com.example.timemanager2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class waiting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        Intent i = getIntent();
        String date=i.getStringExtra("dateForm");
        int s1=i.getIntExtra("Score",0);
        int id = i.getIntExtra("towaitID", 0);
        Intent intent = new Intent(waiting.this, design.class);
        intent.putExtra("startTime", "null");
        intent.putExtra("endTime", "nul");
        intent.putExtra("task", "null");
        intent.putExtra("IDtodesign", id);
        intent.putExtra("dateForm",date);
        intent.putExtra("Score",s1);
        startActivity(intent);
    }
}