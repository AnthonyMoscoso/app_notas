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
import com.example.app_notas.Dialogs.DialogEraseNota;
import com.example.app_notas.Dialogs.DialogListCategorias;
import com.example.app_notas.Interfaces.InterfaceNotas;
import com.example.app_notas.Modales.Categorias;
import com.example.app_notas.Modales.Notas;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements InterfaceNotas {
private Button btnNewNote;
private ListView lvNotes;
private EditText edtxtSearch;
ArrayList<Notas>notasArrayList= new ArrayList<>();
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
                Notas seleccionada = notasArrayList.get(i);
                bundle.putParcelable("Nota",seleccionada);
                bundle.putParcelableArrayList("notas",notasArrayList);
                intent.putExtras(bundle);
                startActivityForResult(intent,CODE);

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
                return true;
            }
        });

    }
    protected  void addNotesToList(){
        adapterNota=new AdapterNota(this,notasArrayList);
        lvNotes.setAdapter(adapterNota);
    }
    protected void GetNotes(){

        Date date=new Date();
        nota= new Notas("Nota 1","Hola soys nota 1",date,null);
        notasArrayList.add(nota);
        nota= new Notas("Nota 2","Hola soys nota 2",date,null);
        notasArrayList.add(nota);
        Categorias dd = new Categorias("deporte",Color.argb(34,34,34,34));
        nota= new Notas("Nota 3","Hola soys nota 3",date,dd);
        notasArrayList.add(nota);
        nota= new Notas("Nota 4","Hola soys nota 4",date,null);
        notasArrayList.add(nota);
        Categorias deporte = new Categorias("deporte",Color.argb(255,45,56,200));
        nota= new Notas("Nota 5","Hola soys nota 5",date,deporte);
        notasArrayList.add(nota);

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
                    final String resul=data.getStringExtra("Estado_Nota");
                    Toast.makeText(this,resul,Toast.LENGTH_LONG).show();

            }
        }
        //GetNotes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void goToCategoriasActivity(MenuItem menuItem){
        Intent intent=new Intent(this,CategoriasActivity.class);
        startActivity(intent);
        Toast.makeText(this,"cerdos",Toast.LENGTH_LONG).show();
    }
    @Override
    public void ObtenerNota(Notas notas) {
        if(notas!=null){
            notasArrayList.remove(notas);
            addNotesToList();
        }
    }
}
