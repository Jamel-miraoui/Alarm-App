package com.example.alarm;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.example.alarm.Alarm;
import java.time.LocalTime;
import java.util.Calendar;

public class MonServive extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("NewApi")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Check if the intent has necessary data to schedule the alarm
        if (intent != null ) {

            Alarm alarm0 = new Alarm("22:30", "AM", true);
            scheduleAlarm(alarm0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    for (int i = 0; i < 10; i++) {
                        Log.i("help", "onStartCommand: "+ alarm0.getTime());
                    }
                }
        }
        return START_NOT_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void scheduleAlarm(Alarm alarm) {
        LocalTime alarmTime = LocalTime.parse(alarm.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarmTime.getHour());
        calendar.set(Calendar.MINUTE, alarmTime.getMinute());
        calendar.set(Calendar.SECOND, 0);
        long alarmMillis = calendar.getTimeInMillis();
        long currentMillis = System.currentTimeMillis();
        if (alarmMillis <= currentMillis) {
            // The scheduled time has already passed
            Log.i("alarm", "Scheduled time has already passed.");
            return;
        }
        while (System.currentTimeMillis() < alarmMillis) {
            try {
                // Adjust the delay for how often you want to check (e.g., every second)
                Thread.sleep(1000);
                Log.i("alarm", String.valueOf(System.currentTimeMillis()) + "  " + alarmMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
