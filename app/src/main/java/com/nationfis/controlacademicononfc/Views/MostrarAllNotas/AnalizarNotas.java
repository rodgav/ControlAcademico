package com.nationfis.controlacademicononfc.Views.MostrarAllNotas;

/*
 * Created by GlobalTIC's on 25/02/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TableLayout;

import com.nationfis.controlacademicononfc.Views.Tabla;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnalizarNotas extends AsyncTask<Void, Void, Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    @SuppressLint("StaticFieldLeak")
    private TableLayout notast;
    AnalizarNotas(Context c, String s, TableLayout notas) {
        this.c = c;
        this.s = s;
        this.notast = notas;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        try {
            JSONObject jo;
            JSONArray ja = new JSONArray(s);

            ArrayList<String> notasc = new ArrayList<>();
            String array[] = {"NOMBRE","UNIDAD 1","UNIDAD 2","UNIDAD 3","UNIDAD 4","PROMEDIO"};
            notasc.addAll(Arrays.asList(array));
            new Tabla(c,notast, notasc,"cabecera").execute();

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                String inicio = jo.getString("nombre");
                String nombre1 = jo.getString("nota1");
                String nombre2 = jo.getString("nota2");
                String nombre3 = jo.getString("nota3");
                String nombre4 = jo.getString("nota4");
                String nombre5 = jo.getString("promedio");

                ArrayList<String> notas = new ArrayList<>();

                notas.add(inicio);
                notas.add(nombre1);
                notas.add(nombre2);
                notas.add(nombre3);
                notas.add(nombre4);
                notas.add(nombre5);

                new Tabla(c,notast, notas,"fila").execute();
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
