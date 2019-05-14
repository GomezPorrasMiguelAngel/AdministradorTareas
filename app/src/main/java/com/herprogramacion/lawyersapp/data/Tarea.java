package com.herprogramacion.lawyersapp.data;

import android.content.ContentValues;
import android.database.Cursor;

import com.herprogramacion.lawyersapp.data.TareaContract.TareaEntry;

import java.util.UUID;

public class Tarea {
    private String id;
    private String idmateria;
    private String idunidad;
    private String nombre;
    private String descripcion;
    private String fecha;
    private String porcentaje;
    private String valor;
    private String estado;

    public Tarea(String id, String idmateria,String idunidad,String nombre,String descripcion
            ,String fecha, String porcentaje, String valor, String estado) {
        this.id = UUID.randomUUID().toString();
        this.idmateria=idmateria;
        this.idunidad=idunidad;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.fecha=fecha;
        this.porcentaje=porcentaje;
        this.valor=valor;
        this.estado=estado;
    }

    public Tarea(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(TareaEntry.ID));
        idmateria = cursor.getString(cursor.getColumnIndex(TareaEntry.IDMATERIA));
        idunidad = cursor.getString(cursor.getColumnIndex(TareaEntry.IDUNIDAD));
        nombre = cursor.getString(cursor.getColumnIndex(TareaEntry.NOMBRE));
        descripcion = cursor.getString(cursor.getColumnIndex(TareaEntry.DESCRIPCION));
         fecha= cursor.getString(cursor.getColumnIndex(TareaEntry.FECHA));
         porcentaje= cursor.getString(cursor.getColumnIndex(TareaEntry.PORCENTAJE));
         valor= cursor.getString(cursor.getColumnIndex(TareaEntry.VALOR));
         estado= cursor.getString(cursor.getColumnIndex(TareaEntry.ESTADO));

    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(TareaEntry.ID, id);
        values.put(TareaEntry.IDMATERIA, idmateria);
        values.put(TareaEntry.IDUNIDAD, idunidad);
        values.put(TareaEntry.NOMBRE, nombre);
        values.put(TareaEntry.DESCRIPCION, descripcion);
        values.put(TareaEntry.FECHA, fecha);
        values.put(TareaEntry.PORCENTAJE, porcentaje);
        values.put(TareaEntry.VALOR, valor);
        values.put(TareaEntry.ESTADO, estado);
        return values;
    }

    public String getId() {
        return id;
    }
    public String getIdMateria() {
        return idmateria;
    }
    public String getIdUnidad() {
        return idunidad;
    }
    public String getnombre() {
        return nombre;
    }
    public String getdescripcion() {
        return descripcion;
    }
    public String getfecha() {
        return fecha;
    }
    public String getporcentaje() {
        return porcentaje;
    }
    public String getvalor() {
        return valor;
    }
    public String getestado() {
        return estado;
    }
    
    

}
