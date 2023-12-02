package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alrmedit);
        Button save = (Button)findViewById(R.id.saveButton);
        Button cansel = (Button)findViewById(R.id.cancelButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
                AlarmDBhelper bd = new AlarmDBhelper(MainActivity2.this);
                int hour = timePicker.getHour()  ;
                int min = timePicker.getMinute();
                Alarm alarm = new Alarm();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    alarm.setTime(alarm.stringToTime(hour +":"+ min));
                    if( hour > 12) {alarm.setDayTime("PM") ; } else{alarm.setDayTime("AM");}
                    alarm.setStatut(true);
                }
                bd.insertAlarm(alarm);
                setResult(RESULT_OK);
                finish();
            }
        });
        cansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}