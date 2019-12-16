package com.example.app_notas.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.DialogFragment;

import com.example.app_notas.Adapters.AdapterCategoria;
import com.example.app_notas.Interfaces.ConexionCategorias;
import com.example.app_notas.Modales.Categorias;
import com.example.app_notas.R;

import java.util.ArrayList;

public class DialogListCategorias extends DialogFragment implements ConexionCategorias {

    private ListView listCategorias;
    private Button btnCancelar,btnNewCategoria;
    private ConexionCategorias obtenerDatos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        View rootView =inflater.inflate(R.layout.dialog_listcategorias,null);


        listCategorias =(ListView)rootView.findViewById(R.id.listVCategorias);
        btnCancelar=(Button)rootView.findViewById(R.id.btnCancelar);
        btnNewCategoria=(Button)rootView.findViewById(R.id.btnNewCategoria);


        Bundle argumentos =getArguments();
        final ArrayList<Categorias> categoriasList = argumentos.getParcelableArrayList("Categorias");

        AdapterCategoria adapterCategoria = new AdapterCategoria(getContext(),categoriasList);
        listCategorias.setAdapter(adapterCategoria);
        listCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(categoriasList.get(i).getNombre().equals("Sin etiqueta")){
                    obtenerDatos.GetCategoria(null);
                }
                else {
                    obtenerDatos.GetCategoria(categoriasList.get(i));
                }

                dismiss();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // respuesta.onRespuesta(null);
                dismiss();
            }
        });
        btnNewCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            obtenerDatos.createCategoria(true);

            }
        });

        return  rootView;

    }

    public void onAttach(Context context){
        super.onAttach(context);
       obtenerDatos=(ConexionCategorias)context;
       // respuesta=(Respuesta)context;

    }

    @Override
    public void GetCategoria(Categorias categorias) {

    }

    @Override
    public void createCategoria(boolean wantCreate) {

    }

    @Override
    public void isNewCategoria(Categorias categorias) {

    }
}
