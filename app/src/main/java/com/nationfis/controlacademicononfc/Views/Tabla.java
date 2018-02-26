package com.nationfis.controlacademicononfc.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.nationfis.controlacademicononfc.R;

import java.util.ArrayList;
import java.util.Objects;


/*
 * Created by GlobalTIC's on 19/02/2018.
 */

public class Tabla extends AsyncTask<Void, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    @SuppressLint("StaticFieldLeak")
    private TableLayout tableLayout;
    private ArrayList<String> arrayList;
    private ArrayList<TableRow> filas;
    private String tipo;
    @SuppressLint("StaticFieldLeak")
    private TableRow fila;

    public Tabla(Context c, TableLayout tableLayout, ArrayList<String> arrayList, String tipo) {
        this.c = c;
        this.tableLayout = tableLayout;
        this.arrayList = arrayList;
        this.tipo = tipo;
        filas = new ArrayList<>();
        fila = new TableRow(c);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        if (Objects.equals(tipo, "fila")) {
            return this.AgregarFila();
        } else if (Objects.equals(tipo, "cabecera")) {
            return this.AgregarCabecera();
        } else {
            return null;
        }

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        tableLayout.addView(fila);
        filas.add(fila);
    }

    private Void AgregarCabecera() {
        TableRow.LayoutParams layoutCelda;

        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        fila.setLayoutParams(layoutFila);

        for (int i = 0; i < arrayList.size(); i++) {
            TextView texto = new TextView(c);
            layoutCelda = new TableRow.LayoutParams(obtenerAnchoPixelesTexto(arrayList.get(i)), TableRow.LayoutParams.WRAP_CONTENT);
            texto.setText(arrayList.get(i));
            texto.setGravity(Gravity.CENTER_HORIZONTAL);
            texto.setTextAppearance(c, R.style.estilo_celda);
            texto.setBackgroundResource(R.drawable.tabla_celda_cabecera);
            texto.setLayoutParams(layoutCelda);
            texto.setTextColor(c.getResources().getColor(R.color.blanco));
            fila.addView(texto);
        }
        return null;
    }

    private Void AgregarFila() {
        TableRow.LayoutParams layoutCelda;
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        fila.setLayoutParams(layoutFila);

        for (int i = 0; i < arrayList.size(); i++) {
            TextView texto = new TextView(c);
            layoutCelda = new TableRow.LayoutParams(obtenerAnchoPixelesTexto(arrayList.get(i)), TableRow.LayoutParams.WRAP_CONTENT);
            texto.setText(arrayList.get(i));
            texto.setGravity(Gravity.CENTER_HORIZONTAL);
            texto.setTextAppearance(c, R.style.estilo_celda);
            texto.setBackgroundResource(R.drawable.tabla_celda);
            texto.setLayoutParams(layoutCelda);

            fila.addView(texto);
        }

        return null;
    }

    private int obtenerAnchoPixelesTexto(String texto) {
        Paint p = new Paint();
        Rect bounds = new Rect();
        p.setTextSize(50);

        p.getTextBounds(texto, 0, texto.length(), bounds);
        return bounds.width();
    }

}
