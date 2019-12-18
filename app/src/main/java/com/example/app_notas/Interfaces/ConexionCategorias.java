package com.example.app_notas.Interfaces;

import com.example.app_notas.Modales.Categorias;
import com.example.app_notas.Modales.Notas;

public interface ConexionCategorias {
    void GetCategoria(Categorias categorias);
    void isWantcreateCategoria(boolean wantCreate);
    void isNewCategoria(Categorias categorias);
    void ObtenerNota(Notas notas);
}
