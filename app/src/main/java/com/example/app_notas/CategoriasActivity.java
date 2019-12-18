package com.example.app_notas;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import com.example.app_notas.Adapters.AdapterCategoria;
import com.example.app_notas.Dialogs.DialogCreateCategoria;
import com.example.app_notas.Dialogs.DialogEraseNota;
import com.example.app_notas.Dialogs.DialogListCategorias;
import com.example.app_notas.Interfaces.ConexionCategorias;
import com.example.app_notas.Modales.Categorias;
import com.example.app_notas.Modales.Notas;
import com.example.app_notas.SQLite.NotesSQLiteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoriasActivity extends AppCompatActivity implements ConexionCategorias {
    private ArrayList<Categorias>categoriasArrayList = new ArrayList<>();
    private ListView lvCategorias;
    private Categorias cate;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvCategorias=(ListView)findViewById(R.id.lvCategorias);
        NotesSQLiteHelper notesSQLiteHelper = new NotesSQLiteHelper(this,"DBNOTES",null,1);
        db=notesSQLiteHelper.getWritableDatabase();
        getCategorias();
        addCategoriasToListView();
        final boolean canDelete=false;

        lvCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        lvCategorias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(!categoriasArrayList.get(position).getNombre().equals("Sin etiqueta")){
                    Bundle argumentos =new Bundle();
                    argumentos.putParcelable("Categoria", categoriasArrayList.get(position));
                    DialogEraseNota dialogEraseNota=new DialogEraseNota();
                    dialogEraseNota.setArguments(argumentos);
                    dialogEraseNota.show(getSupportFragmentManager(),"Categoria");
                }


                return true;
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle argumentos =new Bundle();
                argumentos.putParcelableArrayList("Categorias", categoriasArrayList);
                DialogCreateCategoria dialogCreateCategoria= new DialogCreateCategoria();
                dialogCreateCategoria.setArguments(argumentos);
                dialogCreateCategoria.show(getSupportFragmentManager(),"Categorias");
            }
        });
    }


    private void getCategorias(){
        categoriasArrayList.clear();
        Cursor cursor=db.rawQuery("SELECT * FROM CATEGORIAS ",null);
        if(cursor.moveToFirst()){
            do {

                cate=new Categorias(cursor.getString(0),cursor.getInt(1));
                categoriasArrayList.add(cate);
            }
            while (cursor.moveToNext());
        }
        cate = new Categorias("Sin etiqueta", 0);
        categoriasArrayList.add(cate);

    }
    private void addCategoriasToListView(){
        AdapterCategoria adapterCategoria=new AdapterCategoria(this,categoriasArrayList);
        lvCategorias.setAdapter(adapterCategoria);
    }

    @Override
    public void GetCategoria(Categorias categorias) {
        if(categorias!=null){
            deleteCategoria(categorias);
        }
    }

    @Override
    public void isWantcreateCategoria(boolean wantCreate) {

    }

    @Override
    public void isNewCategoria(Categorias categorias) {
        if(categorias!=null){
            insertarCategoria(categorias);
            addCategoriasToListView();
        }
    }
    public void deleteCategoria(Categorias categorias){
        int res=db.delete("CATEGORIAS","nombre="+"'"+categorias.getNombre()+"'",null);
        if(res!=0){
            Toast.makeText(getApplicationContext(),"Categoria Eliminada",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
        }
        getCategorias();
        addCategoriasToListView();
    }
    public void insertarCategoria(Categorias categoria){
        ContentValues contentValues= new ContentValues();
        contentValues.put("nombre",categoria.getNombre());
        contentValues.put("color",categoria.getColor());
        long res=db.insert("CATEGORIAS",null,contentValues);
        if(res!=-1){
            Toast.makeText(getApplicationContext(),"Nota insetada",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
        }
        getCategorias();
    }
    @Override
    public void ObtenerNota(Notas notas) {

    }
}
