package com.example.alarm;

import java.io.Serializable;

public class Alarm implements Serializable {

    int id ;
    String time ;
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
    public Alarm(String time, String dayTime, boolean statut) {
        this.time = time;
        this.dayTime = dayTime;
        Statut = statut;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
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
}
