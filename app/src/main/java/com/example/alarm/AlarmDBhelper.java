package com.example.alarm;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmDBhelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Alarms" ;
    private static final String Table = "Alarm";
    private static final String id = "id";
    private static final String time = "time";
    private static final String don = "don";
    private static final String Statut = "Statut";

    private static final String REQUETE_CREATION_BD = "create table " + Table + " (" +
            id + " integer primary key autoincrement, " + time + " TEXT not null, " + don + " TEXT not null, " +  // Added a space after "not null"
            Statut + " TEXT not null, " + COLONNE_login + " TEXT not null, " + COLONNE_password + " TEXT not null);";

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
