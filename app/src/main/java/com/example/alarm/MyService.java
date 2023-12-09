package com.example.alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MyService extends Service {
    private Thread Thread;
    private int id;
    private static final String CHANNEL_ID = "channel_id";
    private static final int NOTIFICATION_ID = 1;
    AlarmDBhelper db = new AlarmDBhelper(MyService.this);

    public void onCreate() {
        super.onCreate();
        Thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                scheduleAlarm(db.getAlarmById(id).getTime());
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.id = Integer.parseInt(intent.getStringExtra("id"));
        Log.i("Alarm", "Alarm with ID: " + this.id + " started");
        Thread.start();
        return START_REDELIVER_INTENT;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void scheduleAlarm(String alarmtime) {
        new Thread(() -> {
            LocalTime alarmTime = LocalTime.parse(alarmtime, DateTimeFormatter.ofPattern("HH:mm"));
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTime.getHour());
            calendar.set(Calendar.MINUTE, alarmTime.getMinute());
            calendar.set(Calendar.SECOND, 0);
            long alarmMillis = calendar.getTimeInMillis();
            while (System.currentTimeMillis() < alarmMillis) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Alarm alarmid = db.getAlarmById(id);
                if (alarmid == null) {
                    stopSelf();
                    return;
                }
                Log.i("Alarm", "Service Still work -> Waiting for : " + alarmTime);
            }
            Log.i("Alarm", "Time has passed.");
            stopSelf();
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.deleteAlarm(id);
        Log.i("alarm", "onDestroy: Service destroyed");
    }


}