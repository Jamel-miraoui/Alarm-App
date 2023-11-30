package com.example.alarm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmlist);
        alarmList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            alarmList.add(new Alarm(LocalTime.parse("08:00"), "AM", true));
            alarmList.add(new Alarm(LocalTime.parse("19:30"), "PM", false));
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