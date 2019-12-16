package com.example.app_notas.Dialogs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.DialogFragment;

import com.example.app_notas.Adapters.AdapterCategoria;

import com.example.app_notas.Interfaces.InterfaceNotas;
import com.example.app_notas.Modales.Categorias;
import com.example.app_notas.Modales.Notas;
import com.example.app_notas.R;

import java.util.ArrayList;

public class DialogEraseNota extends DialogFragment implements InterfaceNotas {

    private Button btnYes,btnNot;
    private TextView txtNameNota;
    private ImageView imgvCategoriaColor;
    private InterfaceNotas interfaceNotas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        View rootView =inflater.inflate(R.layout.dialog_erase_categoria,null);

        btnNot=(Button)rootView.findViewById(R.id.btnNot);
        btnYes=(Button)rootView.findViewById(R.id.btnYes);
        txtNameNota=(TextView)rootView.findViewById(R.id.txtNotaName);
        imgvCategoriaColor=(ImageView)rootView.findViewById(R.id.imgvCategoriaColor) ;

        Bundle argumentos =getArguments();
        final Notas nota = argumentos.getParcelable("Nota");
        if(nota.getCategorias()!=null){
            ImageViewCompat.setImageTintList(  imgvCategoriaColor, ColorStateList.valueOf(nota.getCategorias().getColor()));
            imgvCategoriaColor.setImageTintMode(PorterDuff.Mode.SRC_IN);
        }
        txtNameNota.setText(nota.getTitulo());

        btnNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceNotas.ObtenerNota(null);
                dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceNotas.ObtenerNota(nota);
                dismiss();

            }
        });
        return  rootView;

    }

    public void onAttach(Context context){
        super.onAttach(context);
        interfaceNotas=(InterfaceNotas) context;
        // respuesta=(Respuesta)context;

    }

    @Override
    public void ObtenerNota(Notas notas) {

    }
}
