package com.herprogramacion.lawyersapp.data;

import android.provider.BaseColumns;

public class TareaContract {
    public static abstract class TareaEntry implements BaseColumns {
        public static final String TABLE_NAME ="tareas";

        public static final String ID = "id";
        public static final String IDMATERIA = "idmateria";
        public static final String IDUNIDAD = "idunidad";
        public static final String NOMBRE = "nombre";
        public static final String DESCRIPCION = "descripcion";
        public static final String FECHA = "fecha";
        public static final String PORCENTAJE = "porcentaje";
        public static final String VALOR = "valor";
        public static final String ESTADO = "estado";
    }
}
