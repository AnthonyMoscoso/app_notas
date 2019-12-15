package com.example.app_notas.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class NotesSQLiteHelper extends SQLiteOpenHelper {

    String sqlCreateTable_Categorias ="CREATE TABLE CATEGORIAS (" +
            "nombre varchar(20) PRIMARY KEY," +
            "color Integer)";

    String sqlCreateTable_Notas ="CREATE TABLE NOTAS (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " titulo varchar(20)," +
            "contenido TEXT,fecha Date," +
            " Nombre_Categoria varchar(20)," +
            "constraint fk_categorias_notas  FOREIGN KEY(Nombre_Categoria) References CATEGORIAS (nombre))";

    String sqlDropTable_Categorias="DROP TABLE IF EXISTS CATEGORIAS";
    String sqlDropTable_Notas="DROP TABLE IF EXISTS NOTAS";

    public NotesSQLiteHelper( Context context, String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlCreateTable_Categorias);
        sqLiteDatabase.execSQL(sqlCreateTable_Notas);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(sqlDropTable_Categorias);
        sqLiteDatabase.execSQL(sqlDropTable_Notas);

        sqLiteDatabase.execSQL(sqlCreateTable_Categorias);
        sqLiteDatabase.execSQL(sqlCreateTable_Notas);
    }
}
