package com.example.app_notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.*;
import android.widget.Toast;

import com.example.app_notas.Adapters.AdapterNota;
import com.example.app_notas.Modales.Categorias;
import com.example.app_notas.Modales.Notas;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
private Button btnNewNote;
private ListView lvNotes;
private EditText edtxtSearch;
ArrayList<Notas>notas= new ArrayList<>();
private Notas nota;
public  Button del;
private static final int CODE=1;
private AdapterNota adapterNota;
private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        btnNewNote=(Button)findViewById(R.id.btnNewNote);
        lvNotes=(ListView)findViewById(R.id.lvNotes);
        edtxtSearch=(EditText)findViewById(R.id.edtxtBuscar);
        final Button del=(Button)findViewById(R.id.btnDelete) ;
        del.setVisibility(View.INVISIBLE);
        GetNotes();
        addNotesToList();

        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context,WriteActivity.class);
                Bundle bundle = new Bundle();
                Notas seleccionada = notas.get(i);
                bundle.putParcelable("Nota",seleccionada);
                bundle.putParcelableArrayList("notas",notas);
                intent.putExtras(bundle);
                startActivityForResult(intent,CODE);

            }


        });

        lvNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context,"quieres borrarme",Toast.LENGTH_LONG).show();
                del.setVisibility(View.VISIBLE);
                return true;
            }
        });

    }
    protected  void addNotesToList(){
        adapterNota=new AdapterNota(this,notas);
        lvNotes.setAdapter(adapterNota);
    }
    protected void GetNotes(){

        Date date=new Date();
        nota= new Notas("Nota 1","Hola soys nota 1",date,null);
        notas.add(nota);
        nota= new Notas("Nota 2","Hola soys nota 2",date,null);
        notas.add(nota);
        Categorias dd = new Categorias("deporte",Color.argb(34,34,34,34));
        nota= new Notas("Nota 3","Hola soys nota 3",date,dd);
        notas.add(nota);
        nota= new Notas("Nota 4","Hola soys nota 4",date,null);
        notas.add(nota);
        Categorias deporte = new Categorias("deporte",Color.argb(255,45,56,200));
        nota= new Notas("Nota 5","Hola soys nota 5",date,deporte);
        notas.add(nota);

    }

    public void createNewNota(View view){
        Intent intent = new Intent(this,WriteActivity.class);
        Bundle bundle= new Bundle();
        bundle.putParcelableArrayList("notas",notas);
        startActivityForResult(intent,CODE);
    }


    @Override
        public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            if(requestCode==CODE){
                    final String resul=data.getStringExtra("Estado_Nota");
                    Toast.makeText(this,resul,Toast.LENGTH_LONG).show();

            }
        }
        //GetNotes();
    }
}
