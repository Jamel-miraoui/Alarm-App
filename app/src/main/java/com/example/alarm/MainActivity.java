package com.example.alarm;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Alarm> alarmList;
    private AlarmAdapter alarmAdapter;

    private MonServive monService;
    private boolean isBound = false;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MonServive.MonBinder binder = (MonServive.MonBinder) service;
            monService = binder.getService();
            isBound = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                monService.onStartCommand();
            }

            // Now you can call methods on the MonService instance
            // For example:
            // monService.doSomething();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }

    };
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmlist);
        AlarmDBhelper bd = new AlarmDBhelper(MainActivity.this);
        alarmList = bd.getAllAlram();
        for (Alarm alarm:alarmList) {
            Log.i("alarm", "Time: " +alarm.getTime() +"Don : "+ alarm.getDayTime()+"Statut :" +alarm.isStatut()+"Id :"  + alarm.getId());
        }
        alarmAdapter = new AlarmAdapter(this, R.layout.alarm_item_layout, alarmList);
        ListView alarmListView = findViewById(R.id.alarmListView);
        alarmListView.setAdapter(alarmAdapter);
        // Handle Add Alarm button click
        Button addAlarmButton = findViewById(R.id.addAlarmButton);
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivityForResult(intent,1);
            }
        });
        Intent intent = new Intent(this, MonServive.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (1):
                switch (resultCode) {
                    case RESULT_CANCELED:
                        Toast.makeText(this, "Edit cansel", Toast.LENGTH_SHORT).show();
                        return;
                    case RESULT_OK:
                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                }
        }// switch
    }
}