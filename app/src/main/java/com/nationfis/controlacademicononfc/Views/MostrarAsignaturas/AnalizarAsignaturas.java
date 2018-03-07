package com.nationfis.controlacademicononfc.Views.MostrarAsignaturas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Created by Sam on 18/06/2017.
 */

public class AnalizarAsignaturas extends AsyncTask<Void,Void,Integer>{
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView estudiantes;
    private ArrayList<AsignaturasA> asignatura = new ArrayList<>();
    AnalizarAsignaturas(Context c, String s, RecyclerView estudiantes) {
        this.c = c;
        this.s = s;
        this.estudiantes = estudiantes;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        try {
            AsignaturasA asignaturas;
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            asignatura.clear();
            for (int i = 0; i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String nombre = jo.getString("nombre");
                String foto = jo.getString("foto");
                Integer codigo = jo.getInt("codigo");
                String nombrea = jo.getString("nombrea");
                String modo = jo.getString("modo");
                Integer activo = jo.getInt("activo");

                asignaturas = new AsignaturasA();

                asignaturas.setNombre(nombre);
                asignaturas.setFoto(foto);
                asignaturas.setCodigo(codigo);
                asignaturas.setNombrea(nombrea);
                asignaturas.setModo(modo);
                asignaturas.setActivo(activo);
                asignatura.add(asignaturas);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No se pudieron cargar",Toast.LENGTH_SHORT).show();
        }else {
            AdaptadorAsignaturas a = new AdaptadorAsignaturas(c,asignatura);
            estudiantes.setAdapter(a);
        }
    }
}
