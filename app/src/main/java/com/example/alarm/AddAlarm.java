package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class AddAlarm extends AppCompatActivity {
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
                AlarmDBhelper bd = new AlarmDBhelper(AddAlarm.this);
                int hour = timePicker.getHour()  ;
                int min = timePicker.getMinute();
                Alarm alarm = new Alarm();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    alarm.setTime(hour +":"+ min);
                    if( hour > 12) {alarm.setDayTime("PM") ; } else{alarm.setDayTime("AM");}
                    alarm.setStatut(true);
                }
                long insertedId = bd.insertAlarm(alarm);
                Intent intent = new Intent(AddAlarm.this, MyService.class);
                intent.putExtra("id", String.valueOf(insertedId));
                startService(intent);
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