package com.example.alarm;// AlarmAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.alarm.Alarm;

import java.util.List;

public class AlarmAdapter extends ArrayAdapter<Alarm> {

    public AlarmAdapter(@NonNull Context context, int resource, @NonNull List<Alarm> alarms) {
        super(context, resource, alarms);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_item_layout, parent, false);
        }
        Alarm alarm = getItem(position);
        if (alarm != null) {
            TextView timeTextView = convertView.findViewById(R.id.alarmTimeTextView);
            TextView amPmTextView = convertView.findViewById(R.id.alarmDaysTextView);
            Button activateButton = convertView.findViewById(R.id.activateButton);
            // Format LocalTime to display in TextView
            timeTextView.setText(alarm.getTime().toString());
            // Display AM/PM based on the dayTime variable
            amPmTextView.setText(alarm.getDayTime());
            // Set the button state based on the alarm's activation status
            activateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alarm.setStatut(false);
                }
            });
        }
        return convertView;
    }
}