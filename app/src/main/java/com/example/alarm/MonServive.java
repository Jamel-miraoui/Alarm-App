    package com.example.alarm;

    import android.app.Service;
    import android.content.Intent;
    import android.os.Build;
    import android.os.IBinder;
    import android.util.Log;
    import androidx.annotation.RequiresApi;
    import java.time.LocalTime;
    import java.util.Calendar;
    public class MonServive extends Service {
        private Thread Thread;
        private String time ;
        public void onCreate() {
            super.onCreate();
            Thread = new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                   scheduleAlarm(time);
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
            this.time = intent.getStringExtra("time");
            Thread.start();
            return START_STICKY;
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        private void scheduleAlarm(String  alarm) {
            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    Log.i("help", "onStartCommand: " + alarm);
                }
                LocalTime alarmTime = LocalTime.parse(alarm);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarmTime.getHour());
                calendar.set(Calendar.MINUTE, alarmTime.getMinute());
                calendar.set(Calendar.SECOND, 0);
                long alarmMillis = calendar.getTimeInMillis();
                while (System.currentTimeMillis() != alarmMillis) {Log.i("alarm", "waiting for " + alarmTime + "  ") ;}
                Log.i("alarm", "Scheduled time has already passed.");
                Intent intent = new Intent(MonServive.this, AlarmNt.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                stopSelf();
            }).start();
        }
        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.i("alarm", "onDestroy: Service destroyed");
        }
    }