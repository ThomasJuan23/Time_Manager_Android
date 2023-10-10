package com.example.timemanager2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class load extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Intent i = getIntent();
        String date=i.getStringExtra("date");
        int s1=i.getIntExtra("score",0);
        int id = i.getIntExtra("Id", 0);
        Intent intent = new Intent(load.this, Questions.class);
        intent.putExtra("toQuestionID", id);
        intent.putExtra("dateForm",date);
        intent.putExtra("Score",s1);
        startActivity(intent);
    }
}