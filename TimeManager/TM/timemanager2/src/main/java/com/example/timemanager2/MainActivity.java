package com.example.timemanager2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    public static database helper = null;
    private String sql;
    int id = 0, scores = 0;
    String password = "", occupation = "", goal = "";
    SQLiteDatabase db = null;
    private EditText idNumber;
    private  EditText passWord;
    SharedPreferences sp2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        AlarmManager am=(AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
//        Intent in=new Intent("android.intent.action.STARTMYAP");
//        PendingIntent pi= PendingIntent.getBroadcast(this,0,in,1);
//        am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+20000,pi);
      //  Typeface typeface=Typeface.createFromAsset(getAssets(),"font/font1.tff");
        sp2= this.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences sp=this.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        idNumber = findViewById(R.id.MainID);
        passWord = findViewById(R.id.MainPassword);
      //  idNumber.setTypeface(typeface);
        CheckBox rp=findViewById(R.id.rememberPassword);
        CheckBox al=findViewById(R.id.autoLogin);
        if(sp.getBoolean("CHECK",false)){
            rp.setChecked(true);
            int autoID=sp.getInt("USERNAME",0);
            String autoPassword=sp.getString("PASSWORD","");
            String autoId=autoID+"";
            idNumber.setText(autoId);
            passWord.setText(autoPassword);
            if(sp.getBoolean("AUTOLOGIN",false)){
                Intent intent3 = new Intent(MainActivity.this, design.class);
                intent3.putExtra("IDtodesign", autoID);
                intent3.putExtra("startTime", "null");
                intent3.putExtra("endTime", "null");
                intent3.putExtra("task", "null");
                startActivity(intent3);
           //     Toast.makeText(MainActivity.this, "Auto_login", Toast.LENGTH_LONG).show();

            }

        }

        rp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rp.isChecked()){
                    sp.edit().putBoolean("CHECK",true).commit();

                }
                else{
                    sp.edit().putBoolean("CHECK",false).apply();

                }
            }
        });


        al.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(al.isChecked()){
                   sp.edit().putBoolean("AUTOLOGIN",true).commit();
                }
                else{
                    sp.edit().putBoolean("AUTOLOGIN",false).apply();
                }
            }
        });

        String idString = sp2.getString("idNumber", "");
        if (!TextUtils.isEmpty(idString)) {
            idNumber.setText(idString);
        } else {
            idNumber.setText("");
        }
        helper = new database(this);
        db = helper.getWritableDatabase();
        Intent intent = getIntent();
        id = intent.getIntExtra("passID", 0);
        password = intent.getStringExtra("passPassword");
        occupation = intent.getStringExtra("passOccupation");
        goal = intent.getStringExtra("passGoal");
        Button regist = findViewById(R.id.register);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] idss = getAllID();
                //    String pi=idss[0]+"";
                //  Toast.makeText(MainActivity.this, pi, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, register.class);
                intent.putExtra("IDS", idss);
                startActivity(intent);
            }
        });
        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int length = idNumber.getText().length();

                String getPassword = passWord.getText().toString();
                //   String Sid=eid.getText().toString();
                if (length != 0&&!getPassword.equals("")) {
                    int getID = Integer.parseInt(idNumber.getText().toString());
                    String checkPassword = getPassword(getID);
                 //   String checkPassword="";
                    if (getPassword.equals(checkPassword)) {
                        if(rp.isChecked()){
                            SharedPreferences.Editor editor=sp.edit();
                            editor.putInt("USERNAME",getID);
                            editor.putString("PASSWORD",getPassword);
                            editor.apply();
                        }
                        Intent intent2 = new Intent(MainActivity.this, design.class);
                        intent2.putExtra("IDtodesign", getID);
                        intent2.putExtra("startTime", "null");
                        intent2.putExtra("endTime", "null");
                        intent2.putExtra("task", "null");
                        startActivity(intent2);
                    } else {
                        Toast.makeText(MainActivity.this, "Please check your ID and password", Toast.LENGTH_LONG).show();
                        idNumber.setText("");
                        passWord.setText("");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please input your information", Toast.LENGTH_LONG).show();
                }
            }
        });

        if (id != 0) {
            addTable1();
            Toast.makeText(MainActivity.this, "Register Successfully", Toast.LENGTH_LONG).show();
        }
        idNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((!TextUtils.isEmpty(s))) {
                    sp2.edit().putString("idNumber", s.toString()).apply();
                } else {
                    sp2.edit().putString("idNumber", "").apply();
                }
            }
        });

    }

    public String getPassword(int idNumber) {
        db = helper.getWritableDatabase();
        String passWord = "";
        String select = "id=" + idNumber;
        @SuppressLint("Recycle") Cursor cursor = db.query("information", new String[]{"password"}, select, null, null, null, null);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if(cursor.moveToPosition(0)) {
            passWord = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return passWord;
    }

    public void addTable1() {
        db = helper.getWritableDatabase();
        sql = "insert into information (id, password, occupation, goal, scores, skinid, usepoints) values (?, ?, ?, ?, ?, ?, ? )";
        db.execSQL(sql, new Object[]{id, password, occupation, goal, scores,0,0});
        db.close();
    }

    public int[] getAllID() {
        db = helper.getWritableDatabase();
        int[] ids = new int[1000];
        ids[0] = -1;
        Cursor cursor = db.query("information", new String[]{"id"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.moveToPosition(0);
            ids[0] = cursor.getInt(0);
            int j = 1;
            while (cursor.moveToNext()) {
                //  cursor.moveToPosition(j);
                ids[j] = cursor.getInt(0);
            }
            //  String pi=ids[0]+"";
        }
        cursor.close();
        db.close();
        return ids;
    }

}