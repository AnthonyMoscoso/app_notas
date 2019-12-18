package com.example.app_notas.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;

import com.example.app_notas.Interfaces.ConexionCategorias;
import com.example.app_notas.Modales.Categorias;
import com.example.app_notas.R;

import java.util.ArrayList;

public class AdapterCategoria extends ArrayAdapter  {
    private ArrayList<Categorias>arrayCategorias=new ArrayList<>();
    public AdapterCategoria( Context context, ArrayList<Categorias>categorias) {
        super(context, R.layout.adapter_categorias,categorias);
        this.arrayCategorias=categorias;
    }

    public View getView(int position, View convertView, ViewGroup parent){


        View item =convertView;

        Holder holder;

        if(item==null){

            LayoutInflater inflater= LayoutInflater.from(getContext());
            item = inflater.inflate(R.layout.adapter_categorias,null);

            holder = new Holder();

            holder.imgvCategoria=(ImageView)item.findViewById(R.id.imgVColorCategoria);
            holder.txtNombre=(TextView)item.findViewById(R.id.txtNameCategoria);

            item.setTag(holder);
        }
        else{
            holder=(Holder) item.getTag();
        }


        Categorias categoria=arrayCategorias.get(position);

        holder.txtNombre.setText(categoria.getNombre());

        holder.imgvCategoria.setImageResource(R.drawable.badged);
        if(categoria.getColor()!=0){
            ImageViewCompat.setImageTintList(   holder.imgvCategoria, ColorStateList.valueOf(categoria.getColor()));
            holder.imgvCategoria.setImageTintMode(PorterDuff.Mode.SRC_IN);
        }
        return (item);
    }




    public static class Holder {
        ImageView imgvCategoria;
        TextView txtNombre;


    }
}
