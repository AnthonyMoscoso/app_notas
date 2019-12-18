package com.example.app_notas.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class NotesSQLiteHelper extends SQLiteOpenHelper {

    String sqlCreateTable_Categorias ="CREATE TABLE CATEGORIAS (nombre text PRIMARY KEY,color Integer)";

    String sqlCreateTable_Notas ="CREATE TABLE NOTAS ( id INTEGER PRIMARY KEY ,titulo TEXT,contenido TEXT,fecha TEXT ,Nombre_Categoria TEXT)";

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
