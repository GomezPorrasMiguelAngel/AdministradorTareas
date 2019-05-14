package com.herprogramacion.lawyersapp.data;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.herprogramacion.lawyersapp.data.MateriaContract.MateriaEntry;

public class MateriaDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "materias.db";

    public MateriaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MateriaEntry.TABLE_NAME + " ("
                + MateriaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MateriaEntry.ID + " TEXT NOT NULL,"
                + MateriaEntry.NOMBRE + " TEXT NOT NULL,"
                + MateriaEntry.PROFESOR + " TEXT NOT NULL,"
                + MateriaEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + MateriaEntry.ID + "))");

        // Insertar datos ficticios para prueba inicial
        mockData(db);
    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockLawyer(sqLiteDatabase, new Materia("1","Ingles","Jose Alejandro Perez Rosales"));
        mockLawyer(sqLiteDatabase, new Materia("2","Servicio Social","Josefina Lara Cruz"));

    }

    public long mockLawyer(SQLiteDatabase db, Materia materia) {
        return db.insert(
                MateriaEntry.TABLE_NAME,
                null,
                materia.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveLawyer(Materia materia) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                MateriaEntry.TABLE_NAME,
                null,
                materia.toContentValues());

    }

    public Cursor getAllLawyers() {
        return getReadableDatabase()
                .query(
                        MateriaEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getLawyerById(String materiaId) {
        Cursor c = getReadableDatabase().query(
                MateriaEntry.TABLE_NAME,
                null,
                MateriaEntry.ID + " LIKE ?",
                new String[]{materiaId},
                null,
                null,
                null);
        return c;
    }

    public int deleteLawyer(String materiaId) {
        return getWritableDatabase().delete(
                MateriaEntry.TABLE_NAME,
                MateriaEntry.ID + " LIKE ?",
                new String[]{materiaId});
    }

    public int updateLawyer(Materia materia, String materiaId) {
        return getWritableDatabase().update(
                MateriaEntry.TABLE_NAME,
                materia.toContentValues(),
                MateriaEntry.ID + " LIKE ?",
                new String[]{materiaId}
        );
    }
}
