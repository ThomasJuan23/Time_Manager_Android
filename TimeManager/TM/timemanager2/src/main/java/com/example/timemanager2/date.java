package com.example.timemanager2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class date extends AppCompatActivity {
    int year, month, day;
    database helper = MainActivity.helper;
    int skin=0;
    int id=0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date);
        DatePicker dp = findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Intent intent=getIntent();
        id=intent.getIntExtra("IDtodesign",0);
        RelativeLayout main=findViewById(R.id.dateMain);
        skin=getSkin();
        if(skin==1){
            main.setBackground(getDrawable(R.mipmap.bridge));
        }
        else if(skin==2){
            main.setBackground(getDrawable(R.mipmap.water));
        }
        else if(skin==3){
            main.setBackground(getDrawable(R.mipmap.bg2));
        }
        TextView TID = findViewById(R.id.dateID);
        if (id != 0) {
            TID.setText("id: " + id);
        }
        dp.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.this.year = year;
                date.this.month = monthOfYear;
                date.this.day = dayOfMonth;
                String dateForm = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                Intent intent = new Intent(date.this, design.class);
                intent.putExtra("dateForm", dateForm);
                intent.putExtra("startTime", "null");
                intent.putExtra("endTime", "null");
                intent.putExtra("task", "null");
                intent.putExtra("IDtodesign",id);
              //  intent.putExtra("IDtodesign", "null");
                startActivity(intent);
            }
        });
    }
    public int getSkin(){
        int skinId=0;
        SQLiteDatabase db = helper.getWritableDatabase();
        String select = "id="+id;
        Cursor cursor = db.query("information", new String[]{"skinid"}, select, null, null, null, null);
        if (cursor.moveToPosition(0)) {
            skinId = cursor.getInt(0);
            //      Toast.makeText(design.this, state, Toast.LENGTH_LONG).show();
        }
        db.close();
        return  skinId;
    }
}