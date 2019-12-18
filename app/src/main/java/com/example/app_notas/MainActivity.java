package com.example.app_notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.*;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app_notas.Adapters.AdapterNota;
import com.example.app_notas.Dialogs.DialogEraseNota;
import com.example.app_notas.Dialogs.DialogListCategorias;

import com.example.app_notas.Interfaces.ConexionCategorias;
import com.example.app_notas.Modales.Categorias;
import com.example.app_notas.Modales.Notas;
import com.example.app_notas.SQLite.NotesSQLiteHelper;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements ConexionCategorias {
private Button btnNewNote;
private ListView lvNotes;
private EditText edtxtSearch;
ArrayList<Notas>notasArrayList= new ArrayList<>();
private ArrayList<String>nombreCategorias=new ArrayList<>();
private Notas nota;
private static final int CODE=1;
private AdapterNota adapterNota;
private Context context;
private Spinner spinnerCategorias;

private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        btnNewNote=(Button)findViewById(R.id.btnNewNote);
        lvNotes=(ListView)findViewById(R.id.lvNotes);
        edtxtSearch=(EditText)findViewById(R.id.edtxtBuscar);
        spinnerCategorias=(Spinner)findViewById(R.id.spinnerCategorias);



        NotesSQLiteHelper notesSQLiteHelper = new NotesSQLiteHelper(this,"DBNOTES",null,1);
        db=notesSQLiteHelper.getWritableDatabase();
        GetNotes();
        addNotesToList();
        obternerNombreCategorias();

        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context,WriteActivity.class);
                Bundle bundle = new Bundle();
                Notas seleccionada = notasArrayList.get(i);
                bundle.putParcelable("Nota",seleccionada);
                bundle.putParcelableArrayList("notas",notasArrayList);
                intent.putExtras(bundle);
                startActivityForResult(intent,CODE);
                edtxtSearch.setText("");
            }


        });

        lvNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle argumentos =new Bundle();
                argumentos.putParcelable("Nota", notasArrayList.get(i));
                DialogEraseNota dialogEraseNota=new DialogEraseNota();
                dialogEraseNota.setArguments(argumentos);
                dialogEraseNota.show(getSupportFragmentManager(),"Nota");
                edtxtSearch.setText("");
                return true;
            }
        });

        edtxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String palabra=edtxtSearch.getText().toString().trim();
                    if(!palabra.isEmpty()){

                        buscarPorNombre(palabra);
                    }
                    else{
                        GetNotes();
                    }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                notasArrayList.clear();

                String categoriaSeleccionada=nombreCategorias.get(i);
                if(!categoriaSeleccionada.equals("Todas")){
                    mostrarNotasPorCategoriaFromSpinner(nombreCategorias.get(i));
                    addNotesToList();
                }
                else{
                    GetNotes();
                    addNotesToList();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void setElementsToSpinner(){
        ArrayAdapter spinAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,nombreCategorias);
        spinnerCategorias.setAdapter(spinAdapter);
    }
    private void mostrarNotasPorCategoriaFromSpinner(String name){
        Date date=new Date();
        notasArrayList.clear();
        Cursor cursorNotas =db.rawQuery("SELECT * FROM NOTAS WHERE NOMBRE_CATEGORIA='"+name+"'",null);
        Categorias categorias=null;
        if(cursorNotas.moveToFirst()){
            do{
                Cursor cursor=db.rawQuery("SELECT * FROM CATEGORIAS WHERE NOMBRE="+"'"+cursorNotas.getString(4)+"'",null);
                if(cursor.moveToFirst()){
                    do {

                        categorias=new Categorias(cursor.getString(0),cursor.getInt(1));

                    }
                    while (cursor.moveToNext());
                }
                try{
                    Notas notas;
                    notas = new Notas(cursorNotas.getInt(0),cursorNotas.getString(1),cursorNotas.getString(2),date,categorias);
                    categorias=null;
                    notasArrayList.add(notas);
                    addNotesToList();
                }
                catch (SQLException ex ){
                    Log.e("error",ex.getCause()+""+ ex.getMessage());
                }

            }
            while (cursorNotas.moveToNext());
        }

    }
    private void obternerNombreCategorias(){
        nombreCategorias.clear();
        String todas="Todas";
        nombreCategorias.add(todas);
        Cursor cursor = db.rawQuery("SELECT NOMBRE FROM CATEGORIAS ", null);
        if (cursor.moveToFirst()) {
            do {
               String name=cursor.getString(0);
                nombreCategorias.add(name);
            }
            while (cursor.moveToNext());
        }
        setElementsToSpinner();

    }
    private void buscarPorNombre(String cadena){
        Date date=new Date();
        notasArrayList.clear();

        //Toast.makeText(getApplicationContext(),cadena,Toast.LENGTH_LONG).show();
        Cursor cursorNotas =db.rawQuery("SELECT * FROM NOTAS WHERE titulo LIKE  '"+cadena+"%'",null);
        Categorias categorias=null;
        if(cursorNotas.moveToFirst()){
            do{
                Cursor cursor=db.rawQuery("SELECT * FROM CATEGORIAS WHERE NOMBRE="+"'"+cursorNotas.getString(4)+"'",null);
                if(cursor.moveToFirst()){
                    do {

                        categorias=new Categorias(cursor.getString(0),cursor.getInt(1));

                    }
                    while (cursor.moveToNext());
                }
                try{
                    Notas notas;
                    notas = new Notas(cursorNotas.getInt(0),cursorNotas.getString(1),cursorNotas.getString(2),date,categorias);
                    categorias=null;
                    notasArrayList.add(notas);
                    addNotesToList();
                }
                catch (SQLException ex ){
                    Log.e("error",ex.getCause()+""+ ex.getMessage());
                }

            }
            while (cursorNotas.moveToNext());
        }


    }

    protected  void addNotesToList(){
        adapterNota=new AdapterNota(this,notasArrayList);
        lvNotes.setAdapter(adapterNota);
    }
    protected void GetNotes(){

        Date date=new Date();
        notasArrayList.clear();
        Cursor cursorNotas =db.rawQuery("SELECT * FROM NOTAS ",null);
        Categorias categorias=null;
        if(cursorNotas.moveToFirst()){
            do{
                Cursor cursor=db.rawQuery("SELECT * FROM CATEGORIAS WHERE NOMBRE="+"'"+cursorNotas.getString(4)+"'",null);
                if(cursor.moveToFirst()){
                    do {

                         categorias=new Categorias(cursor.getString(0),cursor.getInt(1));

                    }
                    while (cursor.moveToNext());
                }
                try{
                    Notas notas;
                    notas = new Notas(cursorNotas.getInt(0),cursorNotas.getString(1),cursorNotas.getString(2),date,categorias);
                    categorias=null;
                    notasArrayList.add(notas);
                }
                catch (SQLException ex ){
                    Log.e("error",ex.getCause()+""+ ex.getMessage());
                }

            }
            while (cursorNotas.moveToNext());
        }


    }

    public void createNewNota(View view){
        Intent intent = new Intent(this,WriteActivity.class);
        Bundle bundle= new Bundle();
        bundle.putParcelableArrayList("notas",notasArrayList);
        startActivityForResult(intent,CODE);
    }


    @Override
        public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            if(requestCode==CODE){
                 final Bundle bundle=data.getBundleExtra("BUNDLE");

                    final String resul=bundle.getString("Estado_Nota");
                    final Notas note=bundle.getParcelable("NOTA");
                    if(resul.equals("guardada")){
                        createNota(note);
                    }
                    else if(resul.equals("actualizada")){
                        updateNota(note);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),resul,Toast.LENGTH_SHORT).show();
                    }
                 obternerNombreCategorias();
                    setElementsToSpinner();
                GetNotes();
                addNotesToList();
                    Toast.makeText(this,resul,Toast.LENGTH_LONG).show();

            }
        }
        //GetNotes();
    }
    public void updateNota(Notas note){
        ContentValues contentValues= new ContentValues();
        contentValues.put("titulo",note.getTitulo());
        contentValues.put("contenido",note.getContenido());
        contentValues.put("fecha","22");
        if(note.getCategorias()!=null){
            contentValues.put("Nombre_categoria",note.getCategorias().getNombre());

        }
        else{
            contentValues.put("Nombre_categoria","");
        }

        int res=db.update("NOTAS",contentValues,"id="+note.getId(),null);
        if(res!=0){
            Toast.makeText(getApplicationContext(),"Nota Actualizada",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
        }
        GetNotes();
        addNotesToList();
    }
    public void createNota(Notas note){
        ContentValues contentValues= new ContentValues();
        contentValues.put("titulo",note.getTitulo());
        contentValues.put("contenido",note.getContenido());
        contentValues.put("fecha","22");
        if(note.getCategorias()!=null){
            contentValues.put("Nombre_categoria",note.getCategorias().getNombre());
        }


        long res=db.insert("NOTAS",null,contentValues);
        if(res!=-1){
            Toast.makeText(getApplicationContext(),"Nota insetada",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
        }
        GetNotes();
        addNotesToList();
    }
    public  void deleteNota(Notas note){
        int res=db.delete("NOTAS","id="+note.getId(),null);
        if(res!=0){
            Toast.makeText(getApplicationContext(),"Nota Eliminada",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
        }
        GetNotes();
        addNotesToList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void goToCategoriasActivity(MenuItem menuItem){
        Intent intent=new Intent(this,CategoriasActivity.class);
        startActivity(intent);

    }

    @Override
    public void GetCategoria(Categorias categorias) {

    }

    @Override
    public void isWantcreateCategoria(boolean wantCreate) {

    }

    @Override
    public void isNewCategoria(Categorias categorias) {

    }

    @Override
    public void ObtenerNota(Notas notas) {
        if(notas!=null){
            deleteNota(notas);
            addNotesToList();
        }
    }
}
