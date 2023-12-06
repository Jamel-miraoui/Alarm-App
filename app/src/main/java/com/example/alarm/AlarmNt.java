package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlarmNt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmnt);
        playAlarmSound();
    }
    private void playAlarmSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Button bt =(Button)findViewById(R.id.cb);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmNt.this, MainActivity.class);
                startActivity(intent);
                onDestroy();
            }
        });
    }

}