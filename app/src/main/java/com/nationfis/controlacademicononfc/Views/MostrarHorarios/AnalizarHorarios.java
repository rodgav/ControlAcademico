package com.nationfis.controlacademicononfc.Views.MostrarHorarios;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TableLayout;

import com.nationfis.controlacademicononfc.Views.Tabla;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.TAG;


/*
 * Created by GlobalTIC's on 13/02/2018.
 */

public class AnalizarHorarios extends AsyncTask<Void, Void, Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    @SuppressLint("StaticFieldLeak")
    private TableLayout horario;

    AnalizarHorarios(Context c, String s, TableLayout horario) {
        this.c = c;
        this.s = s;
        this.horario = horario;
    }


    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        try {
            JSONObject jo;
            JSONArray ja = new JSONArray(s);

            ArrayList<String> horariosc = new ArrayList<>();
            String array[] = {"HORA","LUNES","MARTES","MIERCOLES","JUEVES","VIERNES","SABADO","DOMINGO"};
            horariosc.addAll(Arrays.asList(array));
            new Tabla(c,horario, horariosc,"cabecera").execute();

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                String inicio = jo.getString("inicio");
                String nombre1 = jo.getString("nombre1");
                String nombre2 = jo.getString("nombre2");
                String nombre3 = jo.getString("nombre3");
                String nombre4 = jo.getString("nombre4");
                String nombre5 = jo.getString("nombre5");
                String nombre6 = jo.getString("nombre6");
                String nombre7 = jo.getString("nombre7");

                ArrayList<String> horarios = new ArrayList<>();

                horarios.add(inicio);
                horarios.add(nombre1);
                horarios.add(nombre2);
                horarios.add(nombre3);
                horarios.add(nombre4);
                horarios.add(nombre5);
                horarios.add(nombre6);
                horarios.add(nombre7);

                new Tabla(c,horario, horarios,"fila").execute();
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
