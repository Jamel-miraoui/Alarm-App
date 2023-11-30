package com.example.alarm;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Alarm {

    int id ;
    LocalTime time ;
    String dayTime;
    boolean Statut ;

    public Alarm() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Alarm(LocalTime time, String dayTime, boolean statut) {
        this.time = time;
        this.dayTime = dayTime;
        Statut = statut;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public boolean isStatut() {
        return Statut;
    }

    public void setStatut(boolean statut) {
        Statut = statut;
    }
    public  String timeToString(){
        return time.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  LocalTime stringToTime(String string){
        LocalTime localTime = LocalTime.parse(string);
        return localTime;
    }

}
