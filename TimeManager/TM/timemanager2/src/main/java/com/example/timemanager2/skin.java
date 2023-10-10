package com.example.timemanager2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class skin extends AppCompatActivity {
    database helper = MainActivity.helper;
    int skin=0;
    int id=0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        Intent i=getIntent();
        id=i.getIntExtra("id",0);
        String date=i.getStringExtra("Date");
        int score=i.getIntExtra("score",0);
        RelativeLayout main=findViewById(R.id.skinMain);
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
        TextView Id=findViewById(R.id.skinID);
        TextView Score=findViewById(R.id.skinScores);
        Id.setText("id:"+id);
        Score.setText("SCORES:"+score);
        Button bridgeImage =findViewById(R.id.bridgeImage);
        Button bridgeText =findViewById(R.id.bridgeText);
        Button waterImage =findViewById(R.id.waterImage);
        Button waterText =findViewById(R.id.waterText);
        Button blueImage =findViewById(R.id.blueImage);
        Button blueText =findViewById(R.id.blueText);
        Button back=findViewById(R.id.sBack);
        back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(TestNetWork()){
                Intent intent=new Intent(skin.this,Score.class);
                intent.putExtra("Id",id);
                intent.putExtra("score", score);
                intent.putExtra("date",date);
                startActivity(intent);}
                else{
                    Intent intent=new Intent(skin.this,warning.class);
                    startActivity(intent);
                }
            }
        });
     //   int finalScore = score;
        bridgeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score>=30){
                changeSkin(1);
                changePoints();
                    Intent intent=new Intent(skin.this,design.class);
                    intent.putExtra("startTime", "null");
                    intent.putExtra("endTime", "null");
                    intent.putExtra("task", "null");
                    intent.putExtra("IDtodesign", id);
                    intent.putExtra("dateForm",date);;
                    intent.putExtra("score",score);
                    startActivity(intent);
            }
                 else{
                Toast.makeText(skin.this, "You need 30 points ", Toast.LENGTH_LONG).show();
            }}
        });
        bridgeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score>=30){
                changeSkin(1);
                changePoints();
                    Intent intent=new Intent(skin.this,design.class);
                    intent.putExtra("startTime", "null");
                    intent.putExtra("endTime", "null");
                    intent.putExtra("task", "null");
                    intent.putExtra("IDtodesign", id);
                    intent.putExtra("dateForm",date);;
                    intent.putExtra("score",score);
                    startActivity(intent);}
                else{
                    Toast.makeText(skin.this, "You need 30 points ", Toast.LENGTH_LONG).show();
                }
            }
        });
        waterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score>=30){
                changeSkin(2);
                changePoints();
                    Intent intent=new Intent(skin.this,design.class);
                    intent.putExtra("startTime", "null");
                    intent.putExtra("endTime", "null");
                    intent.putExtra("task", "null");
                    intent.putExtra("IDtodesign", id);
                    intent.putExtra("dateForm",date);;
                    intent.putExtra("Id",id);
                    intent.putExtra("score",score);
                    startActivity(intent);}
                else{
                    Toast.makeText(skin.this, "You need 30 points ", Toast.LENGTH_LONG).show();
                }
            }
        });
        waterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score>=30){
                changeSkin(2);
                changePoints();
                    Intent intent=new Intent(skin.this,design.class);
                    intent.putExtra("startTime", "null");
                    intent.putExtra("endTime", "null");
                    intent.putExtra("task", "null");
                    intent.putExtra("IDtodesign", id);
                    intent.putExtra("dateForm",date);;
                    intent.putExtra("score",score);
                    startActivity(intent);}
                else{
                    Toast.makeText(skin.this, "You need 30 points ", Toast.LENGTH_LONG).show();
                }
            }
        });
        blueImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score>=30){
                changeSkin(3);
                changePoints();
                    Intent intent=new Intent(skin.this,design.class);
                    intent.putExtra("startTime", "null");
                    intent.putExtra("endTime", "null");
                    intent.putExtra("task", "null");
                    intent.putExtra("IDtodesign", id);
                    intent.putExtra("dateForm",date);;
                    intent.putExtra("score",score);
                    startActivity(intent);}
                else{
                    Toast.makeText(skin.this, "You need 30 points ", Toast.LENGTH_LONG).show();
                }
            }
        });
        blueText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score>=30){
                changeSkin(3);
                changePoints();
                    Intent intent=new Intent(skin.this,design.class);
                    intent.putExtra("startTime", "null");
                    intent.putExtra("endTime", "null");
                    intent.putExtra("task", "null");
                    intent.putExtra("IDtodesign", id);
                    intent.putExtra("dateForm",date);;
                    intent.putExtra("score",score);
                    startActivity(intent);}
                else{
                    Toast.makeText(skin.this, "You need 30 points ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void changeSkin(int skinId){
        SQLiteDatabase db=helper.getWritableDatabase();
        String sql = "update information set skinid="+skinId+" where id="+id;
        db.execSQL(sql);
        db.close();
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

    public void changePoints(){
        int points=0;
        SQLiteDatabase db = helper.getWritableDatabase();
        String select = "id="+id;
        Cursor cursor = db.query("information", new String[]{"usepoints"}, select, null, null, null, null);
        if (cursor.moveToPosition(0)) {
            points = cursor.getInt(0);
            //      Toast.makeText(design.this, state, Toast.LENGTH_LONG).show();
        }
        String sql = "update information set usepoints="+(points-30)+" where id="+id;
        db.execSQL(sql);
        db.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean TestNetWork()
    {
        ConnectivityManager connectivityManager =(ConnectivityManager)getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

        if(activeNetInfo==null){
            return false;
        }
        else{
            boolean netInfo = activeNetInfo.isAvailable();
            if(!netInfo){
                return false;
            }
            else{
                return true;
            }
        }

    }
}