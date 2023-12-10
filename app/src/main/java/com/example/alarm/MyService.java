package com.example.alarm;


import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import java.util.List;

public class MyService extends Service {
    private Thread Thread;
    private int id;
    AlarmDBhelper db = new AlarmDBhelper(MyService.this);
    PendingIntent pendingIntent;
    private static final String CHANNEL_ID = "AlarmApp_channel_id";
    private static final int NOTIFICATION_ID = 1;


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
            try {
                // Check if the app is in the foreground
                boolean isAppInForeground = isAppInForeground();

                // If the app is in the foreground, start the activity directly
                if (isAppInForeground) {
                    Intent intent = new Intent(MyService.this, AlarmNt.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("time",alarmtime);
                    startActivity(intent);
                } else {
                    // If the app is in the background, show the notification
                    showNotification(alarmtime);
                }
            }
            catch (Exception e){
                showNotification(alarmtime);
            }
            stopSelf();
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.deleteAlarm(id);
        Log.i("alarm", "onDestroy: Service destroyed");
    }

    private void showNotification(String time) {
        createNotificationChannel();
        Log.i("Alarm", "Showing Notification");
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Alarm app")
                .setContentText(time + " has passed. Click to view.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true) // Dismisses the notification when clicked
                .build();
        notification.sound = Uri.parse("android.resource://" + getPackageName() + "/"+R.raw.alarm);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Alarm App Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
    private boolean isAppInForeground() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            return topActivity.getPackageName().equals(getPackageName());
        }
        return false;
    }
}