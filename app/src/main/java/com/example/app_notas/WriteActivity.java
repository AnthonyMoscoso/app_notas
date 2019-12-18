package com.example.app_notas;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import com.example.app_notas.Dialogs.DialogCreateCategoria;
import com.example.app_notas.Dialogs.DialogListCategorias;
import com.example.app_notas.Interfaces.ConexionCategorias;
import com.example.app_notas.Modales.Categorias;
import com.example.app_notas.Modales.Notas;
import com.example.app_notas.SQLite.NotesSQLiteHelper;

import java.util.ArrayList;
import java.util.Date;

public class WriteActivity extends AppCompatActivity implements ConexionCategorias {
    private Notas nota;
    ArrayList<Notas> notas = new ArrayList<>();
    ArrayList<Categorias> categoriasArrayList = new ArrayList<>();
    private EditText editTexto, editTitulo;
    private ImageButton imgBtnEtiquetas;
    private TextView txtDate;
    private Categorias categoria;
    private Categorias cate;
    private Categorias cate_obtenida = null;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        editTexto = (EditText) findViewById(R.id.edtTexto);
        imgBtnEtiquetas = (ImageButton) findViewById(R.id.imgBtnEtiqueta);
        txtDate = (TextView) findViewById(R.id.txtDate);
        editTitulo = (EditText) findViewById(R.id.edtTitule);
        NotesSQLiteHelper notesSQLiteHelper = new NotesSQLiteHelper(this, "DBNOTES", null, 1);
        db = notesSQLiteHelper.getWritableDatabase();

        GetBundle();
        getCategorias();

        if (nota != null) {
            editTexto.setText(nota.getContenido());
            editTitulo.setText(nota.getTitulo());
            if (nota.getCategorias() != null) {
                ImageViewCompat.setImageTintList(imgBtnEtiquetas, ColorStateList.valueOf(nota.getCategorias().getColor()));
                imgBtnEtiquetas.setImageTintMode(PorterDuff.Mode.SRC_IN);
                cate_obtenida = nota.getCategorias();
            }

            Toast.makeText(this, nota.toString() + "", Toast.LENGTH_LONG).show();
            // String fecha = nota.getDate().toString();
            // txtDate.setText(fecha);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getCategorias() {
        categoriasArrayList.clear();

        Cursor cursor = db.rawQuery("SELECT * FROM CATEGORIAS ", null);
        if (cursor.moveToFirst()) {
            do {
                cate = new Categorias(cursor.getString(0), cursor.getInt(1));
                categoriasArrayList.add(cate);
            }
            while (cursor.moveToNext());
        }
        cate = new Categorias("Sin etiqueta", 0);
        categoriasArrayList.add(cate);

    }


    public void saveNote(MenuItem menuItem) {
        String text = editTexto.getText().toString();
        String titule = editTitulo.getText().toString();
        Date date = new Date();
        Notas note;
        if (nota != null) {
            note = new Notas(nota.getId(), nota.getTitulo(), text, date, cate_obtenida);
        } else {
            note = new Notas(titule, text, date, cate_obtenida);
        }
        if (notas.size() > 0) {
            if (notas.contains(note)) {

                note.setTitulo(titule);

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("Estado_Nota", "actualizada");
                bundle.putParcelable("NOTA", note);
                intent.putExtra("BUNDLE", bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
                cate_obtenida = null;
            } else {

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("Estado_Nota", "guardada");
                bundle.putParcelable("NOTA", note);

                intent.putExtra("BUNDLE", bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
                cate_obtenida = null;
            }
        } else {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("Estado_Nota", "guardada");
            bundle.putParcelable("NOTA", note);

            intent.putExtra("BUNDLE", bundle);
            setResult(Activity.RESULT_OK, intent);
            cate_obtenida = null;
            finish();
        }

    }


    public void backActivity(MenuItem menuItem) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("Estado_Nota", "cancelado");
        bundle.putParcelable("NOTA", null);
        intent.putExtra("BUNDLE", bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
        cate_obtenida = null;
    }

    public void seleccionarEtiqueta(View view) {
        abrirDialogListCategoria();
    }
    public void GetBundle() {
        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                nota = (Notas) bundle.getParcelable("Nota");
                notas = bundle.getParcelableArrayList("notas");
            }
        } catch (Exception ex) {

        }

    }

    @Override
    public void GetCategoria(Categorias categorias) {
        cate_obtenida = categorias;
        if (nota != null) {
            nota.setCategorias(cate_obtenida);
            if (cate_obtenida != null) {
                ImageViewCompat.setImageTintList(imgBtnEtiquetas, ColorStateList.valueOf(nota.getCategorias().getColor()));
                imgBtnEtiquetas.setImageTintMode(PorterDuff.Mode.SRC_IN);
            } else {
                ImageViewCompat.setImageTintList(imgBtnEtiquetas, ColorStateList.valueOf(0));
                imgBtnEtiquetas.setImageTintMode(null);
            }

        } else {
            if (cate_obtenida != null) {
                ImageViewCompat.setImageTintList(imgBtnEtiquetas, ColorStateList.valueOf(cate_obtenida.getColor()));
                imgBtnEtiquetas.setImageTintMode(PorterDuff.Mode.SRC_IN);
            } else {
                ImageViewCompat.setImageTintList(imgBtnEtiquetas, ColorStateList.valueOf(0));
                imgBtnEtiquetas.setImageTintMode(null);
            }
        }


    }

    @Override
    public void isWantcreateCategoria(boolean wantCreate) {
        if (!wantCreate) {
            abrirDialogListCategoria();
        } else {
            abrirDialogCrearCategoriar();
        }
    }

    public void abrirDialogCrearCategoriar() {
        Bundle argumentos = new Bundle();
        argumentos.putParcelableArrayList("Categorias", categoriasArrayList);
        DialogCreateCategoria dialogCreateCategoria = new DialogCreateCategoria();
        dialogCreateCategoria.setArguments(argumentos);
        dialogCreateCategoria.show(getSupportFragmentManager(), "Categorias");
    }

    public void abrirDialogListCategoria() {
        Bundle argumentos = new Bundle();
        argumentos.putParcelableArrayList("Categorias", categoriasArrayList);
        DialogListCategorias dialogListCategorias = new DialogListCategorias();
        dialogListCategorias.setArguments(argumentos);
        dialogListCategorias.show(getSupportFragmentManager(), "Categorias");
    }

    @Override
    public void isNewCategoria(Categorias categorias) {
        if (categorias != null) {
            createCategoria(categorias);
            getCategorias();
            abrirDialogListCategoria();
        }
    }

    public void createCategoria(Categorias categorias) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", categorias.getNombre());
        contentValues.put("color", categorias.getColor());
        long res = db.insert("CATEGORIAS", null, contentValues);
        if (res != -1) {
            Toast.makeText(getApplicationContext(), "Nota insetada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void ObtenerNota(Notas notas) {

    }
}
