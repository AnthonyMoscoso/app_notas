package com.example.app_notas.Adapters;

import android.content.Context;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.PorterDuff;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;

import android.widget.TextView;


import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.widget.ImageViewCompat;

import com.example.app_notas.Modales.Notas;
import com.example.app_notas.R;


import java.util.ArrayList;


public class AdapterNota extends ArrayAdapter {
    private ArrayList<Notas>arrayNotas= new ArrayList<>();
    public AdapterNota(Context context, ArrayList<Notas>notas) {
        super(context, R.layout.adapter_notas,notas);
        this.arrayNotas=notas;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View item =convertView;
        Holder holder;
        if(item==null){

            LayoutInflater inflater= LayoutInflater.from(getContext());
            item = inflater.inflate(R.layout.adapter_notas,null);
            holder = new Holder();
            holder.imgVEtiqueta=(ImageView)item.findViewById(R.id.imgVetiqueta);
            holder.txtTitulo=(TextView)item.findViewById(R.id.txtTitulo);
            holder.txtFecha=(TextView)item.findViewById(R.id.txtDate);
            item.setTag(holder);
        }
        else{
            holder=(Holder) item.getTag();
        }


        Notas note=arrayNotas.get(position);
        holder.txtTitulo.setText(note.getTitulo());
        String fecha = note.getDate().toString();
        holder.txtFecha.setText(fecha);
        if(note.getCategorias()!=null) {
            holder.imgVEtiqueta.setVisibility(View.VISIBLE);
            holder.imgVEtiqueta.setVisibility(View.VISIBLE);
            ImageViewCompat.setImageTintList(holder.imgVEtiqueta, ColorStateList.valueOf(note.getCategorias().getColor()));
            holder.imgVEtiqueta.setImageTintMode(PorterDuff.Mode.SRC_IN);

        }
        return (item);
    }

    public static class Holder{
        ImageView imgVEtiqueta;
        TextView txtTitulo,txtFecha;
    }
}
