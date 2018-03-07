package com.nationfis.controlacademicononfc.Views.MostrarMatriculados;

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
 * Created by SamGM on 23/04/2017.
 */

public class AnalizadorAsistencia extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView estudiantes;
    private ArrayList<Asistencia>asistencia = new ArrayList<>();
    AnalizadorAsistencia(Context c, String s, RecyclerView estudiantes) {
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
            Asistencia asistencias;
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            for (int i = 0; i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String nombre = jo.getString("nombre");
                String foto = jo.getString("foto");
                Integer codigo = jo.getInt("codigo");
                Integer activo = jo.getInt("activo");

                asistencias = new Asistencia();

                asistencias.setNombre(nombre);
                asistencias.setFoto(foto);
                asistencias.setCodigo(codigo);
                asistencias.setActivo(activo);
                asistencia.add(asistencias);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No se pudo cargar los matriculados",Toast.LENGTH_SHORT).show();
        }else {
            AdaptadorAsistencia a = new AdaptadorAsistencia(c,asistencia);
            estudiantes.setAdapter(a);
        }
    }
}
