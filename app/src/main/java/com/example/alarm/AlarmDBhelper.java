package com.example.alarm;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.time.LocalTime;
import java.util.ArrayList;

public class AlarmDBhelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Alarms" ;
    private static final String Table = "Alarm";
    private static final String id = "id";
    private static final String time = "time";
    private static final String don = "don";
    private static final String Statut = "Statut";

    private static final String REQUETE_CREATION_BD = "create table " + Table + " (" +
            id + " integer primary key autoincrement, " + time + " TEXT not null, " + don + " TEXT not null, " + Statut + " TEXT not null);" ;

    public AlarmDBhelper(Context context, String nom, SQLiteDatabase.CursorFactory cursorfactory, int version) {
        super(context, nom , cursorfactory, version ); }
    public AlarmDBhelper(Context context) {
        super(context, DATABASE_NAME ,null, DATABASE_VERSION
        ); }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(REQUETE_CREATION_BD);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + Table + ";");
        onCreate(db);
    }
    public long insertAlarm(Alarm alarm) {
        SQLiteDatabase maDB = this.getWritableDatabase();
        ContentValues valeurs = new ContentValues();
        valeurs.put(time, alarm.getTime().toString()) ;
        valeurs.put(don, alarm.getDayTime());
        valeurs.put(Statut, alarm.isStatut());
        long insertedId = maDB.insert(Table, null, valeurs);
//      maDB.close();
        return insertedId;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Alarm> getAllAlram() {
        SQLiteDatabase maDb = this.getWritableDatabase();
        Cursor c = maDb.query(Table, new String[]{time,don,Statut,id}, null, null, null, null, null);
        return cursortoAlarms(c);}
    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<Alarm> cursortoAlarms(Cursor c) {
        if (c.getCount() == 0)
            return new ArrayList<>();
        ArrayList<Alarm> alarmList = new ArrayList<>(c.getCount());
        c.moveToFirst();
        do {
            Alarm alarm = new Alarm();
            alarm.setTime(c.getString(0));
            alarm.setDayTime(c.getString(1));
            alarm.setStatut(Integer.parseInt(c.getString(2)) == 1);
            alarm.setId(c.getInt(3));
            alarmList.add(alarm);
        } while (c.moveToNext());
            c.close();
        return alarmList;
    }
    public Alarm getAlarmById(int alarmId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (Alarm alarm : this.getAllAlram()) {
                if (alarm.getId() == alarmId) {
                    return alarm;
                }
            }
        }
        return null;
    }

    public void updateAlarmStatut(int alarmId, boolean newStatut) {
        SQLiteDatabase maDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Statut, newStatut);
        String whereClause = id + "=?";
        String[] whereArgs = {String.valueOf(alarmId)};
        maDB.update(Table, values, whereClause, whereArgs);
    }

    public void deleteAlarm(long id) {
        SQLiteDatabase maDB = this.getWritableDatabase();
        maDB.delete(Table, "id=?", new String[]{String.valueOf(id)});
        maDB.close();
    }

}
