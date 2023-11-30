package com.example.alarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
            id + " integer primary key autoincrement, " + time + " TEXT not null, " + don + " TEXT not null, " +  // Added a space after "not null"
            Statut + " TEXT not null, " + COLONNE_login + " TEXT not null, " + COLONNE_password + " TEXT not null);";

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
        db.execSQL("DROP TABLE " + TableEtudiant + ";");
        onCreate(db);
    }
    public void insertAlarm(Alarm alarm) {
        SQLiteDatabase maDB = this.getWritableDatabase();
        ContentValues valeurs = new ContentValues();
        valeurs.put(COLONNE_prenom, etudiant.getPrenom());
        valeurs.put(COLONNE_nom, etudiant.getNom());
        valeurs.put(COLONNE_classe, etudiant.getClasse());
        valeurs.put(COLONNE_login, etudiant.getLogin());
        valeurs.put(COLONNE_password, etudiant.getPassword());
        maDB.insert(TableEtudiant, null, valeurs);
        maDB.close();
    }
    public ArrayList<Etudiants> getAllEtudiants() {
        SQLiteDatabase maDb = this.getWritableDatabase();
        Cursor c = maDb.query(TableEtudiant, new String[]{COLONNE_id, COLONNE_prenom, COLONNE_nom, COLONNE_classe, COLONNE_login, COLONNE_password}, null, null, null, null, null);
        return cursortoEtudiants(c);
    }

    private ArrayList<Etudiants> cursortoEtudiants(Cursor c) {
        if (c.getCount() == 0)
            return new ArrayList<>();

        ArrayList<Etudiants> etudiantList = new ArrayList<>(c.getCount());
        c.moveToFirst();

        do {
            Etudiants etudiant = new Etudiants();
            etudiant.setId(c.getInt(0));
            etudiant.setPrenom(c.getString(1));
            etudiant.setNom(c.getString(2));
            etudiant.setClasse(c.getString(3));
            etudiant.setLogin(c.getString(4));
            etudiant.setPassword(c.getString(5));
            etudiantList.add(etudiant);
        } while (c.moveToNext());
        c.close();
        return etudiantList;
    }


}
