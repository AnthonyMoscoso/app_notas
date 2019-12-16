package com.example.app_notas;

import android.graphics.Color;
import android.os.Bundle;

import com.example.app_notas.Adapters.AdapterCategoria;
import com.example.app_notas.Dialogs.DialogCreateCategoria;
import com.example.app_notas.Dialogs.DialogListCategorias;
import com.example.app_notas.Interfaces.ConexionCategorias;
import com.example.app_notas.Modales.Categorias;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CategoriasActivity extends AppCompatActivity implements ConexionCategorias {
    private ArrayList<Categorias>categoriasArrayList = new ArrayList<>();
    private ListView lvCategorias;
    private Categorias cate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvCategorias=(ListView)findViewById(R.id.lvCategorias);



        lvCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        lvCategorias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Bundle argumentos =new Bundle();
                argumentos.putParcelableArrayList("Categorias", categoriasArrayList);
                DialogListCategorias dialogListCategorias = new DialogListCategorias();
                dialogListCategorias.setArguments(argumentos);
                dialogListCategorias.show(getSupportFragmentManager(),"Categorias");*/
            }
        });
    }


    private void getCategorias(){
        //oBTENEMOS LAS CATEGORIAS DE LA BASE DE DATOS
        //introduzco datos de prueba , primero ya que la base de datos se conectara al final de la
        //estructuracion de la aplicacion

        cate=new Categorias("Futbol", Color.argb(255,45,56,200));
        categoriasArrayList.add(cate);
        cate=new Categorias("Personal", Color.argb(255,145,56,200));
        categoriasArrayList.add(cate);
        cate=new Categorias("Viajes", Color.argb(255,254,23,34));
        categoriasArrayList.add(cate);
        cate=new Categorias("Eventos", Color.argb(255,67,245,167));
        categoriasArrayList.add(cate);
        cate=new Categorias("Medico", Color.argb(255,67,145,145));
        categoriasArrayList.add(cate);
        cate=new Categorias("Sin etiqueta",0);
        categoriasArrayList.add(cate);
    }
    private void addCategoriasToListView(){
        AdapterCategoria adapterCategoria=new AdapterCategoria(this,categoriasArrayList);
        lvCategorias.setAdapter(adapterCategoria);
    }

    @Override
    public void GetCategoria(Categorias categorias) {

    }

    @Override
    public void createCategoria(boolean wantCreate) {

    }

    @Override
    public void isNewCategoria(Categorias categorias) {
        if(categorias!=null){
            categoriasArrayList.add(categorias);
            addCategoriasToListView();

        }
    }
}
