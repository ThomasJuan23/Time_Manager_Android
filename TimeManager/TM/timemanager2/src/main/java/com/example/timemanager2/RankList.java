package com.example.timemanager2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RankList extends AppCompatActivity {
     int id=0;
     int skin=0;
     int score=0;
    database helper = new database(this);
    TextView rank, list;
    DatabaseThread dt;
    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n", "ResourceAsColor"})
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);
        Intent i=getIntent();
        id=i.getIntExtra("id",0);
        String date=i.getStringExtra("Date");
        score=i.getIntExtra("score",0);
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
        TextView Id=findViewById(R.id.rankID);
        TextView Score=findViewById(R.id.rankscores);
        Id.setText("id:"+id);
        Score.setText("SCORES:"+score);
        Button back=findViewById(R.id.rankBack);
        back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(TestNetWork()){
                dt.interrupt();
                Intent intent=new Intent(RankList.this,Score.class);
                intent.putExtra("Id",id);
                intent.putExtra("score", score);
                intent.putExtra("date",date);
                startActivity(intent);}
                else{
                    Intent i=new Intent(RankList.this,warning.class);
                    startActivity(i);
                }
            }
        });
        rank=findViewById(R.id.rank);
        list=findViewById(R.id.list);
//        Typeface fy=Typeface.createFromAsset(this.getAssets(),"font/font1.ttf");
//        rank.setTypeface(fy);
//        list.setTypeface(fy);
//        rank.setTextColor(R.color.white);
//        list.setTextColor(R.color.white);
     //   list.append("try");
     //   getRank();
        dt= new DatabaseThread(id, list, rank);
        dt.start();
        try {
            dt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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



    static class DatabaseThread extends Thread{
       int idNumber;
       TextView list;
       TextView rank;
//        int score;
        String DRIVER="com.mysql.jdbc.Driver";;
        String URL = "jdbc:mysql://bj-cynosdbmysql-grp-c3nr713k.sql.tencentcdb.com:26327/Time Manager";
        String USER = "root";
        String PASSWORD = "SAyouNAla123";
        DatabaseThread(int idNumber, TextView list, TextView rank){
            this.idNumber=idNumber;
            this.list=list;
            this.rank=rank;
//            this.score=score;
        }
        @SuppressLint("SetTextI18n")
        public void run(){
            try{
                Class.forName(DRIVER);
                Connection conn = (Connection) DriverManager.getConnection(URL,USER,PASSWORD);
                Statement s=conn.createStatement();
                String sql="SELECT * FROM Information order by score desc";
                ResultSet rs=s.executeQuery(sql);
                int j=0;
                rs.beforeFirst();
                while(rs.next()){
                    j++;
                    int i=rs.getInt(1);
                    int point=rs.getInt(2);
                    if(i==idNumber){
                        rank.setText("You are NO "+j);
                    }
             //       Toast.makeText(RankList.this, "try", Toast.LENGTH_LONG).show();
                    list.append("No "+j+": "+i+"--- "+point+"\n");
                }
                rs.close();
                s.close();
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }}
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

//    public void getRank(){
//        String DRIVER="com.mysql.jdbc.Driver";;
//        String URL = "jdbc:mysql://bj-cynosdbmysql-grp-c3nr713k.sql.tencentcdb.com:26327/Time Manager";
//        String USER = "root";
//        String PASSWORD = "SAyouNAla123";
//        int idNumber=id;
//        try{
//            Class.forName(DRIVER);
//            Connection conn = (Connection) DriverManager.getConnection(URL,USER,PASSWORD);
//            Statement s=conn.createStatement();
//            String sql="SELECT * FROM Information order by score desc";
//            ResultSet rs=s.executeQuery(sql);
//            int j=0;
//            rs.beforeFirst();
//            while(rs.next()){
//                j++;
//                int i=rs.getInt(1);
//                int point=rs.getInt(2);
//                if(i==idNumber){
//                    rank.setText("You are NO "+j);
//                }
//                //       Toast.makeText(RankList.this, "try", Toast.LENGTH_LONG).show();
//                list.append("No "+j+": "+i+"--- "+point+"\n");
//            }
//            rs.close();
//            s.close();
//            conn.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}