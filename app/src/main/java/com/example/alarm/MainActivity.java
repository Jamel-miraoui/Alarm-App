package com.example.alarm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AlarmDBhelper bd = new AlarmDBhelper(MainActivity.this);
    private List<Alarm> alarmList = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmlist);
        LinearLayout alarmContainer =(LinearLayout)findViewById(R.id.parent);
        alarmList = bd.getAllAlram();
        for (Alarm alarm : alarmList) {
            View alarmItemView = getLayoutInflater().inflate(R.layout.alarm_item_layout, null);
            TextView alarmTimeTextView = alarmItemView.findViewById(R.id.alarmTimeTextView);
            TextView alarmDaysTextView = alarmItemView.findViewById(R.id.alarmDaysTextView);
            Button supprimerButton = alarmItemView.findViewById(R.id.activateButton);
            alarmTimeTextView.setText(alarm.getTime());
            alarmDaysTextView.setText(alarm.getDayTime());
            supprimerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bd.deleteAlarm(alarm.getId());
                    restartMainActivity();
                }
            });
            alarmContainer.addView(alarmItemView);
        }
        Button addAlarmButton = findViewById(R.id.addAlarmButton);
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MainActivity.this, AddAlarm.class);
                startActivityForResult(intent,1);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
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
                        alarmList = bd.getAllAlram();
                        restartMainActivity();
                        break;
                }
        }
    }
    private void restartMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}