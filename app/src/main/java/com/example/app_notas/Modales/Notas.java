package com.example.app_notas.Modales;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.Date;
import java.util.Objects;

public class Notas implements Parcelable{
    private int id;
    private String titulo;
    private String contenido;
    private Date date;
    private Categorias categorias;

    public Notas(int id, String titulo, String contenido, Date date, Categorias categorias) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.date = date;
        this.categorias = categorias;
    }

    public Notas(String titulo, String contenido, Date date, Categorias categorias) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.date = date;
        this.categorias = categorias;
    }

    protected Notas(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        contenido = in.readString();

        categorias = in.readParcelable(Categorias.class.getClassLoader());
    }

    public static final Creator<Notas> CREATOR = new Creator<Notas>() {
        @Override
        public Notas createFromParcel(Parcel in) {
            return new Notas(in);
        }

        @Override
        public Notas[] newArray(int size) {
            return new Notas[size];
        }
    };

    public void setCategorias(Categorias categorias) {
        this.categorias = categorias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notas)) return false;
        Notas notas = (Notas) o;
        return getTitulo().equals(notas.getTitulo());
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitulo());
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public Date getDate() {
        return date;
    }

    public Categorias getCategorias() {
        return categorias;
    }



    @Override
    public String toString() {
        return "Notas{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", date=" + date +
                ", categorias=" + categorias +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(titulo);
        parcel.writeString(contenido);

        parcel.writeParcelable(categorias, i);
    }
}
