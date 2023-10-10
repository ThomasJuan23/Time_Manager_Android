package com.example.timemanager2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

public class add extends AppCompatActivity {
    int k = 0;
    LocalTime sTime, eTime;
    int shour=-1;
    int sminutes=-1;
    database helper = MainActivity.helper;
    int skin=0;
    int id=0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        Button back=findViewById(R.id.aBack);
        Intent intent = getIntent();
        String date=intent.getStringExtra("dateForm");
        int s1=intent.getIntExtra("Score",0);
        String[] d=date.split("-");
        int year=Integer.parseInt(d[0]);
        int month=Integer.parseInt(d[1]);
        int day=Integer.parseInt(d[2]);
        id = intent.getIntExtra("toaddID", 0);
        LinearLayout main=findViewById(R.id.addMain);
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
        TextView TID = findViewById(R.id.addID);
        if (id != 0) {
            TID.setText("id: " + id);
        }
        EditText addStart = findViewById(R.id.addStart);
        EditText addEnd = findViewById(R.id.addEnd);
        EditText addTask = findViewById(R.id.addTask);
        @SuppressLint("UseCompatLoadingForColorStateLists") ColorStateList white = getResources().getColorStateList(R.color.white);
        @SuppressLint("UseCompatLoadingForColorStateLists") ColorStateList gray = getResources().getColorStateList(R.color.gray);
        @SuppressLint("UseCompatLoadingForColorStateLists") ColorStateList black = getResources().getColorStateList(R.color.black);
        @SuppressLint("UseCompatLoadingForColorStateLists") ColorStateList red = getResources().getColorStateList(R.color.red);

        if(skin==1){
            addStart.setTextColor(white);
            addStart.setHintTextColor(gray);
            addEnd.setTextColor(white);
            addEnd.setHintTextColor(gray);
            addTask.setTextColor(white);
            addTask.setHintTextColor(gray);
        }
        TextView tStart=findViewById(R.id.tStart);
        TextView tEnd=findViewById(R.id.tEnd);
        TextView tTask=findViewById(R.id.tText);
        TextView t1=findViewById(R.id.t1);
        TextView t2=findViewById(R.id.alarm);
        if(skin==2){
            tStart.setTextColor(black);
            tEnd.setTextColor(black);
            tTask.setTextColor(black);
            t1.setTextColor(red);
            t2.setTextColor(red);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(add.this,design.class);
                i.putExtra("startTime", "null");
                i.putExtra("endTime", "null");
                i.putExtra("task", "null");
                i.putExtra("IDtodesign", id);
                i.putExtra("dateForm",date);
                i.putExtra("Score",s1);
                startActivity(i);
            }
        });
        Button confirm = findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String start = addStart.getText().toString();
                String end = addEnd.getText().toString();
                toTime(start,end);
                String task = addTask.getText().toString();

                IntentFilter myIntentFilter = new IntentFilter();
                myIntentFilter.addAction("com.example.timemanager2."+task);
                MyReceiver my=new MyReceiver();
                registerReceiver(my, myIntentFilter);

                if (!start.equals("") && !end.equals("") && !task.equals("")) {
                    if(k==0) {
                        Intent i=new Intent();
                        i.setClass(add.this,MyReceiver.class);
                        i.setAction("com.example.timemanager2."+task);
                        i.putExtra("task", task);
                        PendingIntent PI=PendingIntent.getBroadcast(add.this,0,i,0);
                        AlarmManager alarm= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Calendar c=Calendar.getInstance();
                        c.set(Calendar.DAY_OF_MONTH,day);
                        c.set(Calendar.MONTH,month-1);
                        c.set(Calendar.YEAR,year);
                        c.set(Calendar.HOUR_OF_DAY,shour);
                        c.set(Calendar.MINUTE,sminutes);
                        c.set(Calendar.SECOND,0);
                        alarm.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),PI);
                        Intent intent = new Intent(add.this, design.class);
                        intent.putExtra("startTime", start);
                        intent.putExtra("endTime", end);
                        intent.putExtra("task", task);
                        intent.putExtra("IDtodesign", id);
                        intent.putExtra("dateForm",date);
                        intent.putExtra("Score",s1);
                        startActivity(intent);
                    }
                    else if(k==2){
                        Toast.makeText(add.this, "Start time need to be more early than end time ", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(add.this, "Please input the right time", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(add.this, "Please input the information", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button here=findViewById(R.id.here);
        if(skin==2){
            here.setTextColor(red);
        }
        here.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                ArrayList<Integer> dayss=new ArrayList<>();
                Calendar c=Calendar.getInstance();
                c.set(Calendar.DAY_OF_MONTH,day);
                c.set(Calendar.MONTH,month-1);
                c.set(Calendar.YEAR,year);
                dayss.add(c.get(Calendar.DAY_OF_WEEK));
                ArrayList<Integer> days=new ArrayList<>();
                Intent alarm=new Intent(AlarmClock.ACTION_SET_ALARM);
                String start = addStart.getText().toString();
                String end = addEnd.getText().toString();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    toTime(start,end);
                }
                String task = addTask.getText().toString();
                alarm.putExtra(AlarmClock.EXTRA_HOUR,shour);
                alarm.putExtra(AlarmClock.EXTRA_MINUTES, sminutes);
                alarm.putExtra(AlarmClock.EXTRA_MESSAGE,task);
                alarm.putExtra(AlarmClock.EXTRA_VIBRATE,true);
                alarm.putExtra(AlarmClock.EXTRA_DAYS,dayss);
                alarm.putExtra(AlarmClock.EXTRA_SKIP_UI,true);
                startActivity(alarm);

            }
        });
    }


    public int getSkin(){
        int skinId=0;
        helper=new database(this);
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void toTime(String start, String end) {
        try {
            String [] sts=start.split(":");
            shour=Integer.parseInt(sts[0]);
            sminutes=Integer.parseInt(sts[1]);
            sTime = LocalTime.of(shour,sminutes);
            String [] ets=end.split(":");
            int ehour=Integer.parseInt(ets[0]);
            int eminutes=Integer.parseInt(ets[1]);
            eTime = LocalTime.of(ehour,eminutes);
            k=0;
            if(sTime.compareTo(eTime)==0|| sTime.compareTo(eTime) > 0){
                k=2;
            }
        } catch (Exception e) {
             k = 1;
        }
    }

}