package com.example.app_notas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.example.app_notas.Dialogs.DialogCreateCategoria;
import com.example.app_notas.Dialogs.DialogListCategorias;
import com.example.app_notas.Interfaces.ObtenerCategorias;
import com.example.app_notas.Modales.Categorias;
import com.example.app_notas.Modales.Notas;

import java.util.ArrayList;
import java.util.Date;

public class WriteActivity extends AppCompatActivity implements ObtenerCategorias {
    private Notas nota;
    ArrayList<Notas> notas= new ArrayList<>();
    ArrayList<Categorias> categoriasArrayList = new ArrayList<>();
    private EditText editTexto,editTitulo;
    private ImageButton imgBtnEtiquetas;
    private TextView txtDate;
    private Categorias categoria;
    private Categorias cate;
    private Categorias cate_obtenida=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        editTexto=(EditText)findViewById(R.id.edtTexto);
        imgBtnEtiquetas=(ImageButton)findViewById(R.id.imgBtnEtiqueta);
        txtDate=(TextView)findViewById(R.id.txtDate);
        editTitulo=(EditText)findViewById(R.id.edtTitule) ;
        GetBundle();
        GetEtiqueta();

       if(nota!=null){
            editTexto.setText(nota.getContenido());
            editTitulo.setText(nota.getTitulo());
            if(nota.getCategorias()!=null){
                ImageViewCompat.setImageTintList( imgBtnEtiquetas, ColorStateList.valueOf(nota.getCategorias().getColor()));
                imgBtnEtiquetas.setImageTintMode(PorterDuff.Mode.SRC_IN);
            }

             Toast.makeText(this,nota.toString()+"",Toast.LENGTH_LONG).show();
           // String fecha = nota.getDate().toString();
           // txtDate.setText(fecha);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void GetEtiqueta(){
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
    }

    public void saveNote(MenuItem menuItem){
        String text =editTexto.getText().toString();
        String titule=editTitulo.getText().toString();
        Date date = new Date();
        Notas note;
        if(nota!=null){
            note =new Notas(nota.getTitulo(),text,date,cate_obtenida);
        }
        else{
            note =new Notas(titule,text,date,cate_obtenida);
        }
            if(notas.contains(note)){

                note.setTitulo(titule);
                //UpdateNote()
                Intent intent = new Intent();
                intent.putExtra("Estado_Nota","actualizada");
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
            else {
                //CreateNewNota()
                Intent intent = new Intent();
                intent.putExtra("Estado_Nota","guardada");
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
    }


    public void backActivity(MenuItem menuItem){
        Intent intent = new Intent();
        intent.putExtra("Estado_Nota","cancelado");
        setResult(Activity.RESULT_OK,intent);
        finish();

    }

    public  void seleccionarEtiqueta(View view){


        Bundle argumentos =new Bundle();
        argumentos.putParcelableArrayList("Categorias", categoriasArrayList);
        DialogListCategorias dialogListCategorias = new DialogListCategorias();
        dialogListCategorias.setArguments(argumentos);
        dialogListCategorias.show(getSupportFragmentManager(),"Categorias");

    }
    public void GetBundle(){
        try{
            Bundle bundle =getIntent().getExtras();
            if(bundle!=null){
                nota=(Notas)bundle.getParcelable("Nota");
                notas=bundle.getParcelableArrayList("notas");
            }
        }
        catch (Exception ex){

        }

    }

    @Override
    public void GetCategoria(Categorias categorias) {
       cate_obtenida=categorias;
       nota.setCategorias(cate_obtenida);
       if(cate_obtenida!=null){
           ImageViewCompat.setImageTintList( imgBtnEtiquetas, ColorStateList.valueOf(nota.getCategorias().getColor()));
           imgBtnEtiquetas.setImageTintMode(PorterDuff.Mode.SRC_IN);
       }


    }

    @Override
    public void createCategoria(boolean wantCreate) {
            if(!wantCreate){
                Bundle argumentos =new Bundle();
                argumentos.putParcelableArrayList("Categorias", categoriasArrayList);
                DialogListCategorias dialogListCategorias = new DialogListCategorias();
                dialogListCategorias.setArguments(argumentos);
                dialogListCategorias.show(getSupportFragmentManager(),"Categorias");
            }
            else {
                DialogCreateCategoria dialogCreateCategoria= new DialogCreateCategoria();
                dialogCreateCategoria.show(getSupportFragmentManager(),"");
            }
    }

    @Override
    public void isNewCategoria(Categorias categorias) {
        if(categorias!=null){
            categoriasArrayList.add(categorias);
            Bundle argumentos =new Bundle();
            argumentos.putParcelableArrayList("Categorias", categoriasArrayList);
            DialogListCategorias dialogListCategorias = new DialogListCategorias();
            dialogListCategorias.setArguments(argumentos);
            dialogListCategorias.show(getSupportFragmentManager(),"Categorias");
        }
    }
}
