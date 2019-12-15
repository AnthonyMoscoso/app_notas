package com.example.app_notas.Modales;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Categorias implements Parcelable {
    private String nombre;
    private int color;

    public Categorias(String nombre, int color) {
        this.nombre = nombre;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categorias)) return false;
        Categorias that = (Categorias) o;
        return Objects.equals(getNombre(), that.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre());
    }

    public String getNombre() {
        return nombre;
    }

    public int getColor() {
        return color;
    }

    public static Creator<Categorias> getCREATOR() {
        return CREATOR;
    }

    protected Categorias(Parcel in) {
        nombre = in.readString();
        color = in.readInt();
    }

    public static final Creator<Categorias> CREATOR = new Creator<Categorias>() {
        @Override
        public Categorias createFromParcel(Parcel in) {
            return new Categorias(in);
        }

        @Override
        public Categorias[] newArray(int size) {
            return new Categorias[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Categorias{" +
                "nombre='" + nombre + '\'' +
                ", color=" + color +
                '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeInt(color);
    }
}
