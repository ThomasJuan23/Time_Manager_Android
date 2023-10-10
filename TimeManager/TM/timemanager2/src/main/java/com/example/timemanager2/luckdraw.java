package com.example.timemanager2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class luckdraw extends AppCompatActivity {
    int point=0;
    database helper = MainActivity.helper;
    int skin=0;
    int id=0;
    int score=0;
    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luckdraw);
        Intent i=getIntent();
        id=i.getIntExtra("id",0);
        String date=i.getStringExtra("Date");
        score=i.getIntExtra("score",0);
        RelativeLayout main=findViewById(R.id.luckMain);
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
        TextView Id=findViewById(R.id.luckID);
        TextView Score=findViewById(R.id.luckScores);
        Id.setText("id:"+id);
        Score.setText("SCORES:"+score);
        Button back=findViewById(R.id.lBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(luckdraw.this,design.class);
                intent.putExtra("startTime", "null");
                intent.putExtra("endTime", "null");
                intent.putExtra("task", "null");
                intent.putExtra("IDtodesign", id);
                intent.putExtra("dateForm",date);
                intent.putExtra("score",score);
                startActivity(intent);;
            }
        });
        draw draw=findViewById(R.id.luck);
        Button center=findViewById(R.id.center);
        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score>=5){
                draw.startAnim();
                point=draw.getLuckNumber();
                changePoints(point-5);
                score=score+point-5;
                Score.setText("SCORES:"+score);
                }
                else{
                    Toast.makeText(luckdraw.this, "You need 5 points", Toast.LENGTH_LONG).show();
                }
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
    public void changePoints(int point){
        int points=0;
        SQLiteDatabase db = helper.getWritableDatabase();
        String select = "id="+id;
        Cursor cursor = db.query("information", new String[]{"usepoints"}, select, null, null, null, null);
        if (cursor.moveToPosition(0)) {
            points = cursor.getInt(0);
            //      Toast.makeText(design.this, state, Toast.LENGTH_LONG).show();
        }
        String sql = "update information set usepoints="+(points+point)+" where id="+id;
        db.execSQL(sql);
        db.close();
    }
}