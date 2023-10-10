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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Score extends AppCompatActivity {
    database helper = MainActivity.helper;
    int skin=0;
    int id=0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent s=getIntent();
        id=s.getIntExtra("Id",0);
        int score=s.getIntExtra("score",0);
     //   Toast.makeText(Score.this, score+" id: "+id, Toast.LENGTH_LONG).show();
        String date=s.getStringExtra("date");
        DatabaseThread dt=new DatabaseThread(id,score);
        dt.start();
        try {
            dt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RelativeLayout main=findViewById(R.id.scoreMain);
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
        Button back=findViewById(R.id.scBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dt.interrupt();
                Intent intent=new Intent(Score.this,design.class);
                intent.putExtra("startTime", "null");
                intent.putExtra("endTime", "null");
                intent.putExtra("task", "null");
                intent.putExtra("IDtodesign", id);
                intent.putExtra("dateForm",date);;
                intent.putExtra("Id",id);
                intent.putExtra("score",score);
                startActivity(intent);
            }
        });
        TextView Id=findViewById(R.id.scoreID);
        TextView Score=findViewById(R.id.scores);
        Id.setText("id:"+id);
        Score.setText("SCORES:"+score);
        Button luckDraw=findViewById(R.id.luckDraw);
        Button change=findViewById(R.id.changeSkin);
        luckDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 dt.interrupt();
                 Intent d=new Intent(Score.this,luckdraw.class);
                 d.putExtra("id",id);
                 d.putExtra("score",score);
                 d.putExtra("Date",date);
                 startActivity(d);
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 dt.interrupt();
                 Intent s=new Intent(Score.this,skin.class);
                 s.putExtra("id",id);
                 s.putExtra("score",score);
                 s.putExtra("Date",date);
                 startActivity(s);
            }
        });
        Button rank=findViewById(R.id.rankList);
        rank.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(TestNetWork()){
                dt.interrupt();
                Intent d=new Intent(Score.this,RankList.class);
                d.putExtra("id",id);
                d.putExtra("score",score);
                d.putExtra("Date",date);
                startActivity(d);}
                else{
                    Intent i=new Intent(Score.this,warning.class);
                    startActivity(i);
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

    static class DatabaseThread extends Thread{
        int idNumber;
        int score;
        String DRIVER="com.mysql.jdbc.Driver";;
        String URL = "jdbc:mysql://bj-cynosdbmysql-grp-c3nr713k.sql.tencentcdb.com:26327/Time Manager";
        String USER = "root";
        String PASSWORD = "SAyouNAla123";
        DatabaseThread(int idNumber, int score){
             this.idNumber=idNumber;
             this.score=score;
        }
        public void run(){
            try{
                Class.forName(DRIVER);
                Connection conn = (Connection) DriverManager.getConnection(URL,USER,PASSWORD);
                PreparedStatement ps=conn.prepareStatement("update Information set score="+score+" where id="+idNumber);
                ps.executeUpdate();
                ps.close();
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
//        public ResultSet getScores() throws SQLException {
//            Connection conn = (Connection) DriverManager.getConnection(URL,USER,PASSWORD);
//            PreparedStatement ps=conn.prepareStatement("SELECT * FROM Information Order by score");
//            ps.setInt(1, idNumber);
//            ps.setInt(2,score);
//            ps.executeUpdate();
//            ps.close();
//            conn.close();
//        }
    }

}