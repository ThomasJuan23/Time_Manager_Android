package com.example.timemanager2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {


    private static final String TAG = "database";

    public database(@Nullable Context context) {

        super(context, "tm.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creating database");
        String sql1 = "CREATE TABLE information ( id INT PRIMARY KEY, password Varchar, occupation Varchar, goal Varchar, scores int, skinid int, usepoints int) ";
        db.execSQL(sql1);
        String sql2 = "CREATE TABLE task ( taskID INT PRIMARY KEY, id INT, date Date, timeStart Time, timeEnd Time, task Varchar, ifFinished Varchar) ";
        db.execSQL(sql2);
        //   String TABLE1="CREATE TABLE information ( id INT UNIQUE, password Varchar, occupation VARCHAR, goal VARCHAR, scores int) ";
        // db.execSQL(TABLE1);
        //String TABLE2="CREATE TABLE task ( id INT , date Varchar, start_time VARCHAR, end_time VARCHAR, task VARCHAR, ifFinished VARCHAR, taskID int) ";
        //  db.execSQL(TABLE2);
        //System.out.println("yeah");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
