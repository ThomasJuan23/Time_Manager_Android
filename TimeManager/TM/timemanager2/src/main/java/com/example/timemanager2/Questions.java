package com.example.timemanager2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Questions extends AppCompatActivity {
    int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Intent intent = getIntent();
        String date=intent.getStringExtra("dateForm");
        id = intent.getIntExtra("toQuestionID", 0);
        int s1=intent.getIntExtra("Score",0);
        Random r=new Random();
        int luck=r.nextInt(10);
        String[] questions={"How many movements does a symphony usually have?","In what year was the film born?","What is the country of origin of tulips?","How many points below will you be unable to use the application?","How many points can you use to draw a prize?","How many points can you use to change the skin?","Who is the most handsome teacher?","How many kinds of plants are there in the world?","When was the computer invented?","When was Android introduced to the world?"};     //B,B,A,A,C,C,D,D,D,C
        String[] As={"3","1894","China","-5","30","5","Jordan","Four hundred million","1944","2005"};
        String[] Bs={"4","1895","Ireland","-100","20","10","Thomas","Forty million","1956","2006"};
        String[] Cs={"5","1896","Finland","0","5","30","James","Four million","1934","2007"};
        String[] Ds={"6","1898","Netherlands","2","1","100","Abey","four hundred thousand","1946","2008"};
        TextView question=findViewById(R.id.question);
        question.setText(questions[luck]);
        Button A=findViewById(R.id.A);
        A.setText(As[luck]);
        Button B=findViewById(R.id.B);
        B.setText(Bs[luck]);
        Button C=findViewById(R.id.C);
        C.setText(Cs[luck]);
        Button D=findViewById(R.id.D);
        D.setText(Ds[luck]);
        if(luck==0||luck==1){
            A.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is wrong", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Questions.this,load.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("score", s1);
                    intent.putExtra("date",date);
                    startActivity(intent);
                }
            });
            B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is right", Toast.LENGTH_LONG).show();
                    changePoints();
                    Intent intent=new Intent(Questions.this,design.class);
                    intent.putExtra("startTime", "null");
                    intent.putExtra("endTime", "nul");
                    intent.putExtra("task", "null");
                    intent.putExtra("IDtodesign",id);
                    intent.putExtra("Score", s1);
                    intent.putExtra("dateForm",date);
                    startActivity(intent);
                }
            });
            C.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is wrong", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Questions.this,load.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("score", s1);
                    intent.putExtra("date",date);
                    startActivity(intent);
                }
            });
            D.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is wrong", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Questions.this,load.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("score", s1);
                    intent.putExtra("date",date);
                    startActivity(intent);
                }
            });
        }
        else if(luck==2||luck==3){
            B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is wrong", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Questions.this,load.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("score", s1);
                    intent.putExtra("date",date);
                    startActivity(intent);
                }
            });
            A.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is right", Toast.LENGTH_LONG).show();
                    changePoints();
                    Intent intent=new Intent(Questions.this,design.class);
                    intent.putExtra("startTime", "null");
                    intent.putExtra("endTime", "nul");
                    intent.putExtra("task", "null");
                    intent.putExtra("IDtodesign",id);
                    intent.putExtra("Score", s1);
                    intent.putExtra("dateForm",date);
                    startActivity(intent);
                }
            });
            C.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is wrong", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Questions.this,load.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("score", s1);
                    intent.putExtra("date",date);
                    startActivity(intent);
                }
            });
            D.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is wrong", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Questions.this,load.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("score", s1);
                    intent.putExtra("date",date);
                    startActivity(intent);
                }
            });
        }
        else if(luck==4||luck==5||luck==9){
            B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is wrong", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Questions.this,load.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("score", s1);
                    intent.putExtra("date",date);
                    startActivity(intent);
                }
            });
            C.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is right", Toast.LENGTH_LONG).show();
                    changePoints();
                    Intent intent=new Intent(Questions.this,design.class);
                    intent.putExtra("startTime", "null");
                    intent.putExtra("endTime", "nul");
                    intent.putExtra("task", "null");
                    intent.putExtra("IDtodesign",id);
                    intent.putExtra("Score", s1);
                    intent.putExtra("dateForm",date);
                    startActivity(intent);
                }
            });
            A.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is wrong", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Questions.this,load.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("score", s1);
                    intent.putExtra("date",date);
                    startActivity(intent);
                }
            });
            D.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is wrong", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Questions.this,load.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("score", s1);
                    intent.putExtra("date",date);
                    startActivity(intent);
                }
            });
        }
        else{
            B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is wrong", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Questions.this,load.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("score", s1);
                    intent.putExtra("date",date);
                    startActivity(intent);
                }
            });
            D.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is right", Toast.LENGTH_LONG).show();
                    changePoints();
                    Intent intent=new Intent(Questions.this,design.class);
                    intent.putExtra("startTime", "null");
                    intent.putExtra("endTime", "nul");
                    intent.putExtra("task", "null");
                    intent.putExtra("IDtodesign",id);
                    intent.putExtra("Score", s1);
                    intent.putExtra("dateForm",date);
                    startActivity(intent);
                }
            });
            C.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is wrong", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Questions.this,load.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("score", s1);
                    intent.putExtra("date",date);
                    startActivity(intent);
                }
            });
            A.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Questions.this, "Your answer is wrong", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Questions.this,load.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("score", s1);
                    intent.putExtra("date",date);
                    startActivity(intent);
                }
            });
        }
    }
        public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            Toast.makeText(Questions.this, "You score is too low, so you con't use Time Manager until you answer this question!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    public void changePoints(){
        int points=0;
        database helper = new database(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String select = "id="+id;
        Cursor cursor = db.query("information", new String[]{"usepoints"}, select, null, null, null, null);
        if (cursor.moveToPosition(0)) {
            points = cursor.getInt(0);
            //      Toast.makeText(design.this, state, Toast.LENGTH_LONG).show();
        }
        String sql = "update information set usepoints="+(points+5)+" where id="+id;
        db.execSQL(sql);
        db.close();
    }
}