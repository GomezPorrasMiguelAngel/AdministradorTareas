package com.herprogramacion.lawyersapp.data;
import android.content.ContentValues;
import android.database.Cursor;

import com.herprogramacion.lawyersapp.data.MateriaContract.MateriaEntry;

import java.util.UUID;

public class Materia {
    private String id;
    private String nombre;
    private String profesor;
    private String avatarUri;

    public Materia(String nombre,
                  String profesor) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.profesor = profesor;
        this.avatarUri = avatarUri;

    }
    public Materia(String id,
                   String nombre,String profesor) {
        this.id = id;
        this.nombre = nombre;
        this.profesor = profesor;
        this.avatarUri = avatarUri;

    }
    public Materia(String id,
                   String nombre,String profesor,String avatarUri) {
        this.id = id;
        this.nombre = nombre;
        this.profesor = profesor;
        this.avatarUri = avatarUri;

    }


    public Materia(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(MateriaEntry.ID));
        nombre = cursor.getString(cursor.getColumnIndex(MateriaEntry.NOMBRE));
        profesor = cursor.getString(cursor.getColumnIndex(MateriaEntry.PROFESOR));
        avatarUri = cursor.getString(cursor.getColumnIndex(MateriaContract.MateriaEntry.AVATAR_URI));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(MateriaEntry.ID, id);
        values.put(MateriaEntry.NOMBRE, nombre);
        values.put(MateriaEntry.PROFESOR, profesor);
        values.put(MateriaEntry.AVATAR_URI, avatarUri);
        return values;
    }

    public String getId() {
        return id;
    }

    public String getnombre() {
        return nombre;
    }

    public String getprofesor() {
        return profesor;
    }
    public String getAvatarUri() {
        return avatarUri;
    }

}
