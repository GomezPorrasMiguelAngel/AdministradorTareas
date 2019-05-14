package com.herprogramacion.lawyersapp.data;
import android.provider.BaseColumns;
public class MateriaContract {
    public static abstract class MateriaEntry implements BaseColumns{
        public static final String TABLE_NAME ="materias";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String PROFESOR = "profesor";
        public static final String AVATAR_URI = "avatarUri";
    }
}
