package com.example.timemanager2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Logining extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logining);
        if (TestNetWork()){
        myThread mt=new myThread();
        mt.start();
}       else{
            Intent it=new Intent(Logining.this,warning.class);
            startActivity(it);
        }

    }


    class myThread extends Thread {
        public void run(){
            try{
                sleep(1500);
                Intent it = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(it);
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
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