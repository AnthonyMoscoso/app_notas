package com.example.app_notas.Dialogs;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.app_notas.Interfaces.ConexionCategorias;
import com.example.app_notas.Modales.Categorias;
import com.example.app_notas.Modales.Notas;
import com.example.app_notas.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class DialogCreateCategoria extends DialogFragment implements ConexionCategorias {

    private Button btnAcetpCategoria,btnExit;
    private SeekBar skR,skG,skB;
    private LinearLayout linerColor;
    private ConexionCategorias conexionCategorias;
    private EditText edtNameCategoria;
    private int colorDinamico;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        View rootView =inflater.inflate(R.layout.dialog_crear_categorias,null);



        btnAcetpCategoria=(Button) rootView.findViewById(R.id.btnAceptarNewCategoria);
        btnExit=(Button) rootView.findViewById(R.id.btnExit);
        edtNameCategoria=(EditText)rootView.findViewById(R.id.edtName);
        skR=(SeekBar)rootView.findViewById(R.id.skR);
        skG=(SeekBar)rootView.findViewById(R.id.skG);
        skB=(SeekBar)rootView.findViewById(R.id.skB);
        linerColor=(LinearLayout)rootView.findViewById(R.id.linerColor);
        colorDinamico= Color.argb(255,skR.getProgress(),skG.getProgress(),skB.getProgress());
        linerColor.setBackgroundColor(colorDinamico);
        skR.setMax(255);
        skB.setMax(255);
        skG.setMax(255);
        Bundle argumentos =getArguments();
        final ArrayList<Categorias> categoriasList = argumentos.getParcelableArrayList("Categorias");



        skR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               colorDinamico= Color.argb(255,skR.getProgress(),skG.getProgress(),skB.getProgress());
                linerColor.setBackgroundColor(colorDinamico);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        skB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
              colorDinamico= Color.argb(255,skR.getProgress(),skG.getProgress(),skB.getProgress());
                linerColor.setBackgroundColor(colorDinamico);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        skG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               colorDinamico= Color.argb(255,skR.getProgress(),skG.getProgress(),skB.getProgress());
                linerColor.setBackgroundColor(colorDinamico);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                conexionCategorias.isWantcreateCategoria(false);
                dismiss();
            }
        });
        btnAcetpCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNameCategoria.getText().toString().replace(" ","").length()>0){
                    String categoria=edtNameCategoria.getText().toString();
                    Categorias newCategoria= new Categorias(categoria,colorDinamico);
                    if (categoriasList.contains(newCategoria)) {
                        Snackbar.make(view, "Ya existe una categoria con este nombre", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                    else{
                        dismiss();
                        conexionCategorias.isNewCategoria(newCategoria);
                    }

                }
                else{
                    Toast.makeText(getContext(),"Necesita introducir un nombre primero",Toast.LENGTH_LONG).show();
                }


            }
        });

        return  rootView;

    }

    public void onAttach(Context context){
        super.onAttach(context);
        conexionCategorias =(ConexionCategorias)context;


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

    }
}
