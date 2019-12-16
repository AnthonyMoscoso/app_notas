package com.example.app_notas.Interfaces;

import com.example.app_notas.Modales.Categorias;

public interface ConexionCategorias {
    void GetCategoria(Categorias categorias);
    void createCategoria(boolean wantCreate);
    void isNewCategoria(Categorias categorias);
}
