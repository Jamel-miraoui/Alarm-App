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
        private int  id ;
        AlarmDBhelper db = new AlarmDBhelper(MonServive.this);
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
            Log.i("TAG", "onStartCommand: " + this.id );
            Thread.start();
            return START_STICKY;
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        private void scheduleAlarm(String  alarmtime) {
            new Thread(() -> {
                LocalTime alarmTime = LocalTime.parse(alarmtime);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarmTime.getHour());
                calendar.set(Calendar.MINUTE, alarmTime.getMinute());
                calendar.set(Calendar.SECOND, 0);
                long alarmMillis = calendar.getTimeInMillis();
                while (System.currentTimeMillis() < alarmMillis) {
                    Alarm alarmid = db.getAlarmById(id);
                    if(alarmid == null){
                        stopSelf();
                        return;
                    }
                    Log.i("alarm", "waiting for " + alarmTime) ;
                }
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