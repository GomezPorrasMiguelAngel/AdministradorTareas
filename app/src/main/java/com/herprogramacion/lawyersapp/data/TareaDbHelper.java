package com.herprogramacion.lawyersapp.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TareaDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tareas.db";

    public TareaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TareaContract.TareaEntry.TABLE_NAME + " ("
                + TareaContract.TareaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TareaContract.TareaEntry.ID + " TEXT NOT NULL,"
                + TareaContract.TareaEntry.IDMATERIA + " TEXT NOT NULL,"
                + TareaContract.TareaEntry.IDUNIDAD + " TEXT NOT NULL,"
                + TareaContract.TareaEntry.NOMBRE + " TEXT NOT NULL,"
                + TareaContract.TareaEntry.DESCRIPCION + " TEXT NOT NULL,"
                + TareaContract.TareaEntry.FECHA + " TEXT,"
                + TareaContract.TareaEntry.PORCENTAJE + " TEXT,"
                + TareaContract.TareaEntry.VALOR + " TEXT,"
                + TareaContract.TareaEntry.ESTADO + " TEXT,"
                + "UNIQUE (" + TareaContract.TareaEntry.ID + "))");

        // Insertar datos ficticios para prueba inicial
        //mockData(db);
    }

    /*private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockLawyer(sqLiteDatabase, new Tarea("1","Ingles","Jose Alejandro Perez Rosales"));
        mockLawyer(sqLiteDatabase, new Tarea("2","Servicio Social","Josefina Lara Cruz"));

    }*/

    public long mockLawyer(SQLiteDatabase db, Tarea tarea) {
        return db.insert(
                TareaContract.TareaEntry.TABLE_NAME,
                null,
                tarea.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveLawyer(Tarea tarea) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                TareaContract.TareaEntry.TABLE_NAME,
                null,
                tarea.toContentValues());

    }

    public Cursor getAllLawyers() {
        return getReadableDatabase()
                .query(
                        TareaContract.TareaEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getAllLawyersidunidad() {
        return getReadableDatabase()
                .query(
                        TareaContract.TareaEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        TareaContract.TareaEntry.IDUNIDAD,
                        null,
                        TareaContract.TareaEntry.IDUNIDAD);
    }

    public Cursor getLawyerById(String tareaId) {
        Cursor c = getReadableDatabase().query(
                TareaContract.TareaEntry.TABLE_NAME,
                null,
                TareaContract.TareaEntry.ID + " LIKE ?",
                new String[]{tareaId},
                null,
                null,
                null);
        return c;
    }

    public Cursor getLawyerByIdMateria(String materiaId) {
        Cursor c = getReadableDatabase().query(
                TareaContract.TareaEntry.TABLE_NAME,
                null,
                TareaContract.TareaEntry.IDMATERIA + " LIKE ?",
                new String[]{materiaId},
                null,
                null,
                null);
        return c;
    }

    public int deleteLawyer(String tareaId) {
        return getWritableDatabase().delete(
                TareaContract.TareaEntry.TABLE_NAME,
                TareaContract.TareaEntry.ID + " LIKE ?",
                new String[]{tareaId});
    }

    public int updateLawyer(Tarea tarea, String tareaId) {
        return getWritableDatabase().update(
                TareaContract.TareaEntry.TABLE_NAME,
                tarea.toContentValues(),
                TareaContract.TareaEntry.ID + " LIKE ?",
                new String[]{tareaId}
        );
    }
}