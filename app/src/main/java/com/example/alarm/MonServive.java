    package com.example.alarm;

    import static androidx.core.app.ActivityCompat.startActivityForResult;

    import android.annotation.SuppressLint;
    import android.app.Notification;
    import android.app.Service;
    import android.content.Intent;
    import android.media.MediaPlayer;
    import android.os.Binder;
    import android.os.Build;
    import android.os.IBinder;
    import android.util.Log;
    import androidx.annotation.Nullable;
    import androidx.annotation.RequiresApi;
    import com.example.alarm.Alarm;
    import java.time.LocalTime;
    import java.util.Calendar;

    public class MonServive extends Service {
        private final IBinder binder = new MonBinder();
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return binder;
        }
        @SuppressLint("NotificationId0")
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            if (intent != null) {
                Alarm alarm = new Alarm("16:50", "AM", true);
                scheduleAlarm(alarm);
            }
            startForeground(0, new Notification());
            return START_NOT_STICKY;
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        private void scheduleAlarm(Alarm alarm) {
            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    Log.i("help", "onStartCommand: " + alarm.getTime());
                }
                LocalTime alarmTime = LocalTime.parse(alarm.getTime());
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarmTime.getHour());
                calendar.set(Calendar.MINUTE, alarmTime.getMinute());
                calendar.set(Calendar.SECOND, 0);
                long alarmMillis = calendar.getTimeInMillis();
                long currentMillis = System.currentTimeMillis();
                while (System.currentTimeMillis() <= alarmMillis) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Log.i("alarm", String.valueOf(System.currentTimeMillis()));
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

        public class MonBinder extends Binder {
            MonServive getService() {
                // Return the instance of MonService for clients to interact with
                return MonServive.this;
            }
        }

    }