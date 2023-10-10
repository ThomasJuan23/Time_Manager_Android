package com.example.timemanager2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static androidx.core.graphics.BlendModeCompat.COLOR;

public class design extends AppCompatActivity {
    int id = 0, taskID = 0, skin=0;
    String timeStart = "null", timeEnd = "", task = "", ifFinished = "unfinished";
    database helper = new database(this);
    String[] st = new String[10];
    String[] et = new String[10];
   // String[] f = new String[10];
    String[] t = new String[10];
    String theDate = "null";
    int s1=0;
    int k=0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SetTextI18n", "ResourceAsColor", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.design);
        Button s=findViewById(R.id.score);
        SharedPreferences sp=this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        st[9] = "null";
        Intent intent = getIntent();
        id = intent.getIntExtra("IDtodesign", 0);
        TextView TID = findViewById(R.id.designID);
        if (id != 0) {
            TID.setText("id: " + id);
        }
        LinearLayout main=findViewById(R.id.designMain);
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
        Button date = findViewById(R.id.date);
        Button out=findViewById(R.id.Logout);
        out.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=sp.edit();
//                editor.putInt("USERNAME",0);
//                editor.putString("PASSWORD","");
//                editor.putBoolean("CHECK",false);
                editor.putBoolean("AUTOLOGIN",false);
                editor.apply();
                Intent i =new Intent(design.this,MainActivity.class);
                startActivity(i);
            }
        });
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dateForm = year + "-" + (month + 1) + "-" + day;
        Intent i = getIntent();
        String dateForm1 = i.getStringExtra("dateForm");
        if (dateForm1 != null) {
            dateForm = dateForm1;
        }
        date.setText(dateForm);
        @SuppressLint("UseCompatLoadingForColorStateLists") ColorStateList white = getResources().getColorStateList(R.color.white);
        if(skin==1){
            date.setTextColor(white);}
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(design.this, date.class);
                intent.putExtra("IDtodesign",id);
                startActivity(intent);
            }
        });
        theDate = dateForm;
        Button add = findViewById(R.id.add);
        Intent i2 = getIntent();
        timeStart = i2.getStringExtra("startTime");
        timeEnd = i2.getStringExtra("endTime");
        task = i2.getStringExtra("task");
        s1=i2.getIntExtra("Score",0);
        taskID = getTaskID();
        if (!timeStart.equals("null")&&st[9].equals("null")) {
            Toast.makeText(design.this, "add successfully", Toast.LENGTH_LONG).show();
            addTable2();
        }
            s1=getScores();
        s.setText("scores: "+s1);
        getTask();
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TestNetWork()){
                Intent sco=new Intent(design.this, Score.class);
                sco.putExtra("Id",id);
                sco.putExtra("score",getScores());
                sco.putExtra("date",theDate);
                startActivity(sco);}
                else{
                    Intent i=new Intent(design.this,warning.class);
                    startActivity(i);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (st[9].equals("null")) {
                    Intent intent = new Intent(design.this, add.class);
                    intent.putExtra("dateForm",theDate);
                    intent.putExtra("toaddID", id);
                    intent.putExtra("Score",s1);
                    startActivity(intent);
                } else {
                    Toast.makeText(design.this, "This day is too busy", Toast.LENGTH_LONG).show();
                }
            }
        });
        @SuppressLint("UseCompatLoadingForColorStateLists") ColorStateList black = getResources().getColorStateList(R.color.black);
        TextView Tline11 = findViewById(R.id.line11);
        Tline11.setText(st[0]);
        TextView Tline12 = findViewById(R.id.line21);
        Tline12.setText(et[0]);
        TextView Tline13 = findViewById(R.id.line31);
        Tline13.setText(t[0]);
        TextView Tline21 = findViewById(R.id.line12);
        Tline21.setText(st[1]);
        TextView Tline22 = findViewById(R.id.line22);
        Tline22.setText(et[1]);
        TextView Tline23 = findViewById(R.id.line32);
        Tline23.setText(t[1]);
        TextView Tline31 = findViewById(R.id.line13);
        Tline31.setText(st[2]);
        TextView Tline32 = findViewById(R.id.line23);
        Tline32.setText(et[2]);
        TextView Tline33 = findViewById(R.id.line33);
        Tline33.setText(t[2]);
        TextView Tline41 = findViewById(R.id.line14);
        Tline41.setText(st[3]);
        TextView Tline42 = findViewById(R.id.line24);
        Tline42.setText(et[3]);
        TextView Tline43 = findViewById(R.id.line34);
        Tline43.setText(t[3]);
        TextView Tline51 = findViewById(R.id.line15);
        Tline51.setText(st[4]);
        TextView Tline52 = findViewById(R.id.line25);
        Tline52.setText(et[4]);
        TextView Tline53 = findViewById(R.id.line35);
        Tline53.setText(t[4]);
        TextView Tline61 = findViewById(R.id.line16);
        Tline61.setText(st[5]);
        TextView Tline62 = findViewById(R.id.line26);
        Tline62.setText(et[5]);
        TextView Tline63 = findViewById(R.id.line36);
        Tline63.setText(t[5]);
        TextView Tline71 = findViewById(R.id.line17);
        Tline71.setText(st[6]);
        TextView Tline72 = findViewById(R.id.line27);
        Tline72.setText(et[6]);
        TextView Tline73 = findViewById(R.id.line37);
        Tline73.setText(t[6]);
        TextView Tline81 = findViewById(R.id.line18);
        Tline81.setText(st[7]);
        TextView Tline82 = findViewById(R.id.line28);
        Tline82.setText(et[7]);
        TextView Tline83 = findViewById(R.id.line38);
        Tline83.setText(t[7]);
        TextView Tline91 = findViewById(R.id.line19);
        Tline91.setText(st[8]);
        TextView Tline92 = findViewById(R.id.line29);
        Tline92.setText(et[8]);
        TextView Tline93 = findViewById(R.id.line39);
        Tline93.setText(t[8]);
        TextView Tline01 = findViewById(R.id.line110);
        Tline01.setText(st[9]);
        if (st[9].equals("null")) {
            Tline01.setText("");
        }
        TextView Tline02 = findViewById(R.id.line210);
        Tline02.setText(et[9]);
        TextView Tline03 = findViewById(R.id.line310);
        Tline03.setText(t[9]);
        TextView Tline1=findViewById(R.id.t1);
        TextView Tline2=findViewById(R.id.t2);
        TextView Tline3=findViewById(R.id.t3);
        TextView Tline4=findViewById(R.id.t4);
        if(skin==2){
            Tline1.setTextColor(black);
            Tline2.setTextColor(black);
            Tline3.setTextColor(black);
            Tline4.setTextColor(black);
            Tline11.setTextColor(black);
            Tline12.setTextColor(black);
            Tline13.setTextColor(black);
            Tline21.setTextColor(black);
            Tline22.setTextColor(black);
            Tline23.setTextColor(black);
            Tline31.setTextColor(black);
            Tline32.setTextColor(black);
            Tline33.setTextColor(black);
            Tline41.setTextColor(black);
            Tline42.setTextColor(black);
            Tline43.setTextColor(black);
            Tline51.setTextColor(black);
            Tline52.setTextColor(black);
            Tline53.setTextColor(black);
            Tline61.setTextColor(black);
            Tline62.setTextColor(black);
            Tline63.setTextColor(black);
            Tline71.setTextColor(black);
            Tline72.setTextColor(black);
            Tline73.setTextColor(black);
            Tline81.setTextColor(black);
            Tline82.setTextColor(black);
            Tline83.setTextColor(black);
            Tline91.setTextColor(black);
            Tline92.setTextColor(black);
            Tline93.setTextColor(black);
            Tline01.setTextColor(black);
            Tline02.setTextColor(black);
            Tline03.setTextColor(black);
        }
        Tline13.setMovementMethod(ScrollingMovementMethod.getInstance());
        Tline23.setMovementMethod(ScrollingMovementMethod.getInstance());
        Tline33.setMovementMethod(ScrollingMovementMethod.getInstance());
        Tline43.setMovementMethod(ScrollingMovementMethod.getInstance());
        Tline53.setMovementMethod(ScrollingMovementMethod.getInstance());
        Tline63.setMovementMethod(ScrollingMovementMethod.getInstance());
        Tline73.setMovementMethod(ScrollingMovementMethod.getInstance());
        Tline83.setMovementMethod(ScrollingMovementMethod.getInstance());
        Tline93.setMovementMethod(ScrollingMovementMethod.getInstance());
        Tline03.setMovementMethod(ScrollingMovementMethod.getInstance());
        Button BLine1 = findViewById(R.id.line41);
        @SuppressLint("UseCompatLoadingForColorStateLists") ColorStateList color = getResources().getColorStateList(R.color.yellow);
        @SuppressLint("UseCompatLoadingForColorStateLists") ColorStateList red = getResources().getColorStateList(R.color.red);
        String state = "unfinished";
        state = getState(st[0], et[0]);
        if (state.equals("unfinished")) {
            BLine1.setTextColor(color);
            if(skin==2){
                BLine1.setTextColor(red);
            }
            BLine1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getTime(theDate,st[0],et[0]);
                    if(k==2){

                        BLine1.setText("FINISHED");
                        BLine1.setTextColor(white);
                        updateState(st[0], et[0]);
                        s.setText("scores: "+getScores());
                    }
                    else if(k==3){
                        Toast.makeText(design.this, "Time is not up yet", Toast.LENGTH_SHORT).show();
                    }
                    else if(k==4){
                        Toast.makeText(design.this, "Time has passed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            BLine1.setText("FINISHED");
            BLine1.setTextColor(white);
        }
        Button BLine2 = findViewById(R.id.line42);
        BLine2.setTextColor(color);
        if(skin==2){
            BLine2.setTextColor(red);
        }
        state = getState(st[1], et[1]);

        if (state.equals("unfinished")) {
            BLine2.setTextColor(color);
            if(skin==2){
                BLine2.setTextColor(red);
            }
           // if(k==2){
           // if(now.compareTo(ldt[0]) > 0 &&now.compareTo(ldt[0])==0&&now.compareTo(ldt[1])==0&& now.compareTo(ldt[1]) < 0) {
                BLine2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        getTime(theDate,st[1],et[1]);
                        if(k==2){

                            BLine2.setText("FINISHED");
                            BLine2.setTextColor(white);
                            updateState(st[1], et[1]);
                            s.setText("scores: "+getScores());}
                        else if(k==3){
                            Toast.makeText(design.this, "Time is not up yet", Toast.LENGTH_SHORT).show();
                        }
                        else if(k==4){
                            Toast.makeText(design.this, "Time has passed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        } else {
            BLine2.setText("FINISHED");
            BLine2.setTextColor(white);
        }

        Button BLine3 = findViewById(R.id.line43);
        BLine3.setTextColor(color);
        if(skin==2){
            BLine3.setTextColor(red);
        }
        state = getState(st[2], et[2]);
        if (state.equals("unfinished")) {
            BLine3.setTextColor(color);
            if(skin==2){
                BLine3.setTextColor(red);
            }
            BLine3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getTime(theDate,st[2],et[2]);
                    if(k==2){
                        BLine3.setText("FINISHED");
                        BLine3.setTextColor(white);
                        updateState(st[2], et[2]);
                        s.setText("scores: "+getScores());}
                    else if(k==3){
                        Toast.makeText(design.this, "Time is not up yet", Toast.LENGTH_SHORT).show();
                    }
                    else if(k==4){
                        Toast.makeText(design.this, "Time has passed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            BLine3.setText("FINISHED");
            BLine3.setTextColor(white);
        }

        Button BLine4 = findViewById(R.id.line44);
        BLine4.setTextColor(color);
        if(skin==2){
            BLine4.setTextColor(red);
        }
        state = getState(st[3], et[3]);
        if (state.equals("unfinished")) {
            BLine4.setTextColor(color);
            if(skin==2){
                BLine4.setTextColor(red);
            }
            BLine4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getTime(theDate,st[3],et[3]);
                    if(k==2){
                        BLine4.setText("FINISHED");
                        BLine4.setTextColor(white);
                        updateState(st[3], et[3]);
                        s.setText("scores: "+getScores());}
                    else if(k==3){
                        Toast.makeText(design.this, "Time is not up yet", Toast.LENGTH_SHORT).show();
                    }
                    else if(k==4){
                        Toast.makeText(design.this, "Time has passed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            BLine4.setText("FINISHED");
            BLine4.setTextColor(white);
        }
        Button BLine5 = findViewById(R.id.line45);
        BLine5.setTextColor(color);
        if(skin==2){
            BLine5.setTextColor(red);
        }
        state = getState(st[4], et[4]);
        if (state.equals("unfinished")) {
            BLine5.setTextColor(color);
            if(skin==2){
                BLine5.setTextColor(red);
            }
            BLine5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getTime(theDate,st[4],et[4]);
                    if(k==2){
                        BLine5.setText("FINISHED");
                        BLine5.setTextColor(white);
                        updateState(st[4], et[4]);
                        s.setText("scores: "+getScores());}
                    else if(k==3){
                        Toast.makeText(design.this, "Time is not up yet", Toast.LENGTH_SHORT).show();
                    }
                    else if(k==4){
                        Toast.makeText(design.this, "Time has passed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            BLine5.setText("FINISHED");
            BLine5.setTextColor(white);
        }
        Button BLine6 = findViewById(R.id.line46);
        BLine6.setTextColor(color);
        if(skin==2){
            BLine6.setTextColor(red);
        }
        state = getState(st[5], et[5]);
        if (state.equals("unfinished")) {
            BLine6.setTextColor(color);
            if(skin==2){
                BLine6.setTextColor(red);
            }
            BLine6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getTime(theDate,st[5],et[5]);
                    if(k==2){
                        BLine6.setText("FINISHED");
                        BLine6.setTextColor(white);
                        updateState(st[5], et[5]);
                        s.setText("scores: "+getScores());}
                    else if(k==3){
                        Toast.makeText(design.this, "Time is not up yet", Toast.LENGTH_SHORT).show();
                    }
                    else if(k==4){
                        Toast.makeText(design.this, "Time has passed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            BLine6.setText("FINISHED");
            BLine6.setTextColor(white);
        }
        Button BLine7 = findViewById(R.id.line47);
        BLine7.setTextColor(color);
        if(skin==2){
            BLine7.setTextColor(red);
        }
        state = getState(st[6], et[6]);
        if (state.equals("unfinished")) {
            BLine7.setTextColor(color);
            if(skin==2){
                BLine7.setTextColor(red);
            }
            BLine7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getTime(theDate,st[6],et[6]);
                    if(k==2){

                        BLine7.setText("FINISHED");
                        BLine7.setTextColor(white);
                        updateState(st[6], et[6]);
                        s.setText("scores: "+getScores());}
                    else if(k==3){
                        Toast.makeText(design.this, "Time is not up yet", Toast.LENGTH_SHORT).show();
                    }
                    else if(k==4){
                        Toast.makeText(design.this, "Time has passed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            BLine7.setText("FINISHED");
            BLine7.setTextColor(white);
        }
        Button BLine8 = findViewById(R.id.line48);
        BLine8.setTextColor(color);
        if(skin==2){
            BLine8.setTextColor(red);
        }
        state = getState(st[7], et[7]);
        if (state.equals("unfinished")) {
            BLine8.setTextColor(color);
            if(skin==2){
                BLine8.setTextColor(red);
            }
            BLine8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getTime(theDate,st[7],et[7]);
                    if(k==2){
                        BLine8.setText("FINISHED");
                        BLine8.setTextColor(white);
                        updateState(st[7], et[7]);
                        s.setText("scores: "+getScores());}
                    else if(k==3){
                        Toast.makeText(design.this, "Time is not up yet", Toast.LENGTH_SHORT).show();
                    }
                    else if(k==4){
                        Toast.makeText(design.this, "Time has passed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            BLine8.setText("FINISHED");
            BLine8.setTextColor(white);
        }
        Button BLine9 = findViewById(R.id.line49);
        if(skin==2){
            BLine9.setTextColor(red);
        }
        BLine9.setTextColor(color);
        state = getState(st[8], et[8]);
        if (state.equals("unfinished")) {
            BLine9.setTextColor(color);
            if(skin==2){
                BLine9.setTextColor(red);
            }
            BLine9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getTime(theDate,st[8],et[8]);
                    if(k==2){
                        BLine9.setText("FINISHED");
                        BLine9.setTextColor(white);
                        updateState(st[8], et[8]);
                        s.setText("scores: "+getScores());}
                    else if(k==3){
                        Toast.makeText(design.this, "Time is not up yet", Toast.LENGTH_SHORT).show();
                    }
                    else if(k==4){
                        Toast.makeText(design.this, "Time has passed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            BLine9.setText("FINISHED");
            BLine9.setTextColor(white);
        }
        Button BLine10 = findViewById(R.id.line410);
        BLine10.setTextColor(color);
        if(skin==2){
            BLine10.setTextColor(red);
        }
        state = getState(st[9], et[9]);
        if (state.equals("unfinished")) {
            BLine10.setTextColor(color);
            if(skin==2){
                BLine10.setTextColor(red);
            }
            BLine10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getTime(theDate,st[9],et[9]);
                    if(k==2){

                        BLine10.setText("FINISHED");
                        BLine10.setTextColor(white);
                        updateState(st[9], et[9]);
                        s.setText("scores: "+getScores());}
                    else if(k==3){
                        Toast.makeText(design.this, "Time is not up yet", Toast.LENGTH_SHORT).show();
                    }
                    else if(k==4){
                        Toast.makeText(design.this, "Time has passed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            BLine10.setText("FINISHED");
            BLine10.setTextColor(white);
        }


        Button back1=findViewById(R.id.remove1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(theDate,st[0],et[0]);
                if(k==3) {
                    remove(st[0], et[0], t[0]);
                    Intent intent = new Intent(design.this, waiting.class);
                    intent.putExtra("dateForm", theDate);
                    intent.putExtra("towaitID", id);
                    intent.putExtra("Score", s1);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(design.this, "You can not remove it, because it has started", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button back2=findViewById(R.id.remove2);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(theDate,st[1],et[1]);
                if(k==3) {
                remove(st[1],et[1],t[1]);
                Intent intent = new Intent(design.this, waiting.class);
                intent.putExtra("dateForm",theDate);
                intent.putExtra("towaitID", id);
                intent.putExtra("Score",s1);
                startActivity(intent);
            }
                else{
                    Toast.makeText(design.this, "You can not remove it, because it has started", Toast.LENGTH_SHORT).show();
                }}
        });
        Button back3=findViewById(R.id.remove3);
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(theDate,st[2],et[2]);
                if(k==3) {
                remove(st[2],et[2],t[2]);
                Intent intent = new Intent(design.this, waiting.class);
                intent.putExtra("dateForm",theDate);
                intent.putExtra("towaitID", id);
                intent.putExtra("Score",s1);
                startActivity(intent);
            }
                else{
                    Toast.makeText(design.this, "You can not remove it, because it has started", Toast.LENGTH_SHORT).show();
                }}
        });
        Button back4=findViewById(R.id.remove4);
        back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(theDate,st[3],et[3]);
                if(k==3) {
                remove(st[3],et[3],t[3]);
                Intent intent = new Intent(design.this, waiting.class);
                intent.putExtra("dateForm",theDate);
                intent.putExtra("towaitID", id);
                intent.putExtra("Score",s1);
                startActivity(intent);
            }
                else{
                    Toast.makeText(design.this, "You can not remove it, because it has started", Toast.LENGTH_SHORT).show();
                }}
        });
        Button back5=findViewById(R.id.remove5);
        back5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(theDate,st[4],et[4]);
                if(k==3) {
                remove(st[4],et[4],t[4]);
                Intent intent = new Intent(design.this, waiting.class);
                intent.putExtra("dateForm",theDate);
                intent.putExtra("towaitID", id);
                intent.putExtra("Score",s1);
                startActivity(intent);
            }
                else{
                    Toast.makeText(design.this, "You can not remove it, because it has started", Toast.LENGTH_SHORT).show();
                }}
        });
        Button back6=findViewById(R.id.remove6);
        back6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(theDate,st[5],et[5]);
                if(k==3) {
                remove(st[5],et[5],t[5]);
                Intent intent = new Intent(design.this, waiting.class);
                intent.putExtra("dateForm",theDate);
                intent.putExtra("towaitID", id);
                intent.putExtra("Score",s1);
                startActivity(intent);
            }
                else{
                    Toast.makeText(design.this, "You can not remove it, because it has started", Toast.LENGTH_SHORT).show();
                }}
        });
        Button back7=findViewById(R.id.remove7);
        back7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(theDate,st[6],et[6]);
                if(k==3) {
                remove(st[6],et[6],t[6]);
                Intent intent = new Intent(design.this, waiting.class);
                intent.putExtra("dateForm",theDate);
                intent.putExtra("towaitID", id);
                intent.putExtra("Score",s1);
                startActivity(intent);
            }
                else{
                    Toast.makeText(design.this, "You can not remove it, because it has started", Toast.LENGTH_SHORT).show();
                }}
        });
        Button back8=findViewById(R.id.remove8);
        back8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(theDate,st[7],et[7]);
                if(k==3) {
                remove(st[7],et[7],t[7]);
                Intent intent = new Intent(design.this, waiting.class);
                intent.putExtra("dateForm",theDate);
                intent.putExtra("towaitID", id);
                intent.putExtra("Score",s1);
                startActivity(intent);
            }
                else{
                    Toast.makeText(design.this, "You can not remove it, because it has started", Toast.LENGTH_SHORT).show();
                }}
        });
        Button back9=findViewById(R.id.remove9);
        back9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(theDate,st[8],et[8]);
                if(k==3) {
                remove(st[8],et[8],t[8]);
                Intent intent = new Intent(design.this, waiting.class);
                intent.putExtra("dateForm",theDate);
                intent.putExtra("towaitID", id);
                intent.putExtra("Score",s1);
                startActivity(intent);
            }
                else{
                    Toast.makeText(design.this, "You can not remove it, because it has started", Toast.LENGTH_SHORT).show();
                }}
        });
        Button back0=findViewById(R.id.remove0);
        back0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(theDate,st[9],et[9]);
                if(k==3) {
                remove(st[9],et[9],t[9]);
                Intent intent = new Intent(design.this, waiting.class);
                intent.putExtra("dateForm",theDate);
                intent.putExtra("towaitID", id);
                intent.putExtra("Score",s1);
                startActivity(intent);
            }
                else{
                    Toast.makeText(design.this, "You can not remove it, because it has started", Toast.LENGTH_SHORT).show();
                }}
        });
        if(getScores()<=-5){
            Intent intents = new Intent(design.this,Questions.class);
            intents.putExtra("dateForm",theDate);
            intents.putExtra("toQuestionID", id);
            intents.putExtra("Score",s1);
            startActivity(intents);
        }
    }

    public void updateState(String start_time, String end_time) {
        helper=new database(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update task set ifFinished='finished' where timeStart='" + start_time + "' and timeEnd='" + end_time + "'";
        db.execSQL(sql);
        db.close();
    }

    public void addTable2() {
        // taskID=getTaskId();
        helper=new database(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into task (taskID, id, date, timeStart, timeEnd, task, ifFinished) values (?, ?, ?, ?, ?, ?, ? )";
        db.execSQL(sql, new Object[]{taskID, id, theDate, timeStart, timeEnd, task, ifFinished});
        db.close();
    }

    public int getTaskID() {
        helper=new database(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        int i = 0;
        Cursor cursor = db.query("task", new String[]{"taskID"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            i = 1;
            while (cursor.moveToNext()) {
                i++;
            }
        }
        cursor.close();
        db.close();
        return i + 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void remove(String st, String et, String t){
        helper=new database(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String select = "timeStart='" + st + "' and timeEnd='" + et + "' and task='"+t+"'";
        Cursor cursor=db.query("task",new String[]{"taskID"},select,null,null,null,null);
        cursor.moveToPosition(0);
        int tID=cursor.getInt(0);
        String sql="update task set taskID=taskID-1 where taskID>"+tID;
        db.delete("task","timeStart=? and timeEnd=? and task=?",new String[]{st,et,t});
        db.execSQL(sql);
        db.close();
    }




    public void getTask() {
        helper=new database(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String select = "id=" + id + " and date='" + theDate + "'";
        Cursor cursor = db.query("task", new String[]{"timeStart", "timeEnd", "task"}, select, null, null, null, null);
        if (cursor.moveToPosition(0)) {
            st[0] = cursor.getString(0);
            et[0] = cursor.getString(1);
            t[0] = cursor.getString(2);
            int i = 1;
            while (cursor.moveToNext()) {
                st[i] = cursor.getString(0);
                et[i] = cursor.getString(1);
                t[i] = cursor.getString(2);
                i++;
            }
        }
        cursor.close();
        db.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getScores(){
        int score=0;
        helper= new database(this);
        SQLiteDatabase db=helper.getWritableDatabase();
        String select1="ifFinished='unfinished' and id="+id;
        int neg=0;
        String[] dates =new String[10000];
        String[] timeEnds =new String[10000];
        @SuppressLint("Recycle") Cursor cursor = db.query("task", new String[]{"date","timeEnd"}, select1, null, null, null, null);
        if (cursor.moveToFirst()) {
            neg = 1;
            dates[neg-1]=cursor.getString(0);
            timeEnds[neg-1]=cursor.getString(1);
            while (cursor.moveToNext()) {
                neg++;
                dates[neg-1]=cursor.getString(0);
                timeEnds[neg-1]=cursor.getString(1);
            }
        }
        int i=0;
        int minus=0;
        int p=0;
        String[]  ldts=new String[10000];
        while(i<neg){
            String[] d=dates[i].split("-");
            String[] ets=timeEnds[i].split(":");
            int year2=Integer.parseInt(d[0]);
            int month2=Integer.parseInt(d[1]);
            int day2=Integer.parseInt(d[2]);
            int ehour2=Integer.parseInt(ets[0]);
            int eminute2=Integer.parseInt(ets[1]);
//            if(i==1){
//            String abc=year2+"-"+month2+"-"+day2+" "+ehour2+":"+eminute2;
//            Toast.makeText(design.this, abc, Toast.LENGTH_SHORT).show();}
            LocalDateTime ldt=LocalDateTime.of(year2,month2,day2,ehour2,eminute2,0);
            ldts[i]=ldt.toString();
            System.out.println(ldts[i]);
            LocalDateTime now2=LocalDateTime.now();
            if(ldt.compareTo(now2)<0){
            //    p=i;
                minus++;
            }
            i++;
        }
        int pos=0;
      //  int f3=0;
        String select2="ifFinished='finished' and id="+id;
        @SuppressLint("Recycle") Cursor cursor2 = db.query("task", new String[]{"taskID"}, select2, null, null, null, null);
        if (cursor2.moveToFirst()) {
            pos = 1;
       //     f3=cursor2.getInt(0);
            while (cursor2.moveToNext()) {
                pos++;
            }
        }
        score=pos-minus;
        int points=0;
        String select3 = "id="+id;
        Cursor cursor3 = db.query("information", new String[]{"usepoints"}, select3, null, null, null, null);
        if (cursor3.moveToPosition(0)) {
            points = cursor3.getInt(0);
        }
        score=score+points;
        if(score!=0){
            String sql = "update information set scores="+score+" where id="+id;
            db.execSQL(sql);
           }
        cursor.close();
        cursor2.close();
        db.close();
      //  String  abc="Score:"+score+" pos:"+pos+" neg:"+minus+date+now+" id:"+id+" f3:"+f3+" neg:"+neg+" p:"+p;
       // String abc=ldts[0]+ldts[1]+ldts[2]+ldts[3]+ldts[4]+ldts[5]+ldts[6];
    //    String abc=timeEnds[0]+timeEnds[1]+timeEnds[2]+timeEnds[3]+timeEnds[4]+timeEnds[5];
      //  Toast.makeText(design.this, abc, Toast.LENGTH_SHORT).show();
        return score;
    }





    public String getState(String start_time, String end_time) {
        String state = "unfinished";
        helper = new database(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String select = "timeStart='" + start_time + "' and timeEnd='" + end_time + "' and " + "id=" + id + " and date='" + theDate + "'";
        Cursor cursor = db.query("task", new String[]{"ifFinished"}, select, null, null, null, null);
        if (cursor.moveToPosition(0)) {
            state = cursor.getString(0);
            //      Toast.makeText(design.this, state, Toast.LENGTH_LONG).show();
        }

        cursor.close();
        db.close();
        return state;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getTime(String theDate, String timeStart, String timeEnd){
        LocalDateTime[] ldt=new LocalDateTime[2];
      //  String p="null";
        k=0;
        try{
           String[] d=theDate.split("-");
           String[] sts=timeStart.split(":");
           String[] ets=timeEnd.split(":");
           int year=Integer.parseInt(d[0]);
           int month=Integer.parseInt(d[1]);
           int day=Integer.parseInt(d[2]);
           int shour=Integer.parseInt(sts[0]);
           int sminute=Integer.parseInt(sts[1]);
           int ehour=Integer.parseInt(ets[0]);
           int eminute=Integer.parseInt(ets[1]);
           ldt[0]=LocalDateTime.of(year,month,day,shour,sminute,0);
           ldt[1]=LocalDateTime.of(year,month,day,ehour,eminute,0);
            LocalDateTime now=LocalDateTime.now();
//            DateTimeFormatter sdf= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");;
//            String n1=now.format(sdf);
//            String n2=ldt[0].format(sdf);
//            String n3=ldt[1].format(sdf);
//            p=n1+" | "+n2+" | "+n3;



            if((now.compareTo(ldt[0]) > 0 ||now.compareTo(ldt[0])==0)&&(now.compareTo(ldt[1])==0|| now.compareTo(ldt[1]) < 0)) {
                k=2;
            }
            else if(now.compareTo(ldt[0])<0){
                k=3;
            }
            else if(now.compareTo(ldt[1])>0){
                k=4;
            }
        }
        catch (Exception e){
            k=1;
        }


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
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK ){
//            Toast.makeText(design.this, "You must answer this question", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }
}