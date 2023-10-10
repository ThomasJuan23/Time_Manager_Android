package com.example.timemanager2;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {
    int id=0;
    String start="null";
    String end="";
    String task="";
    String date="";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        task=intent.getStringExtra("task");
        if (intent.getAction().equals(action)) {
            Toast.makeText(context, task, Toast.LENGTH_LONG).show();
            Intent intent2= new Intent(context, MainActivity.class);
            intent2.putExtra("startTime", start);
            intent2.putExtra("endTime", end);
            intent2.putExtra("task", task);
            intent2.putExtra("IDtodesign", id);
            intent2.putExtra("dateForm",date);
         //   Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    intent2, 0);
            NotificationManager manager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            if(Build.VERSION.SDK_INT>=26) {
                int importance = NotificationManager.IMPORTANCE_MAX;
                @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel("channel_1", "123", importance);
                channel.enableLights(true);
                channel.enableVibration(true);
                long[] vibration={200, 300, 400, 1000, 500, 2000, 900, 3000};
                channel.setVibrationPattern(vibration);
                channel.setLightColor(R.color.red);
                manager.createNotificationChannel(channel);
                @SuppressLint("WrongConstant") Notification notify = new Notification.Builder(context, "channel_1")
                        .setCategory(Notification.CATEGORY_EVENT)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setTicker("TickerText:" + "Excuse me!")
                        .setContentTitle("It is time to " + task)
                        .setContentText("Click here to finish")
                        .setContentIntent(pendingIntent)
                        .setNumber(1)
                        .setAutoCancel(true)
                        .build();
                notify.defaults = Notification.DEFAULT_ALL;
                manager.notify(1, notify);


            }
            else{
                @SuppressLint("WrongConstant") Notification notify = new Notification.Builder(context)
                        .setSmallIcon(R.mipmap.logo)
                        .setTicker("TickerText:" + "Excuse me!")
                        .setContentTitle("Time Manager: It is time to " + task)
                        .setContentText("Click here to finish")
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setNumber(1)
                        .setAutoCancel(true)
                        .build();
                notify.defaults = Notification.DEFAULT_ALL;
                manager.notify(1, notify);
            }

        }
    }



}
