package com.nationfis.controlacademicononfc.Views.ComprobarAsistencia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/*
 * Created by Sam on 25/04/2017.
 */

public class AnalizadorComprobarAsistencia extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s,tipo;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView estudiantes;
    private ArrayList<AsistenciaCA>asistenciaCAs = new ArrayList<>();
    private Integer a;
    AnalizadorComprobarAsistencia(Context c, String s, RecyclerView estudiantes, String tipo, Integer a) {
        this.c = c;
        this.s = s;
        this.estudiantes = estudiantes;
        this.tipo = tipo;
        this.a = a;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        if (Objects.equals(a,1)){
            try {
                JSONObject jo;
                JSONArray ja = new JSONArray(s);
                AsistenciaCA asistenciaCA;
                asistenciaCAs.clear();
                for (int i = 0;i<ja.length();i++){
                    jo=ja.getJSONObject(i);
                    String nombre = jo.getString("nombre");
                    Integer codigo = jo.getInt("codigo");
                    String foto = jo.getString("foto");
                    Integer asistio = jo.getInt("asistio");

                    asistenciaCA = new AsistenciaCA();
                    asistenciaCA.setNombre(nombre);
                    asistenciaCA.setCodigo(codigo);
                    asistenciaCA.setFoto(foto);
                    asistenciaCA.setAsistio(asistio);
                    asistenciaCAs.add(asistenciaCA);

                }
                return 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            try {
                JSONObject jo;
                JSONArray ja = new JSONArray(s);
                AsistenciaCA asistenciaCA;
                asistenciaCAs.clear();
                for (int i = 0;i<ja.length();i++){
                    jo=ja.getJSONObject(i);
                    String nombre = jo.getString("nombre");
                    String asignatura = jo.getString("asignatura");
                    String foto = jo.getString("foto");
                    Integer asistio = jo.getInt("asistio");

                    asistenciaCA = new AsistenciaCA();
                    asistenciaCA.setNombre(nombre);
                    asistenciaCA.setAsignatura(asignatura);
                    asistenciaCA.setFoto(foto);
                    asistenciaCA.setAsistio(asistio);
                    asistenciaCAs.add(asistenciaCA);

                }
                return 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No se cargo la asistencia",Toast.LENGTH_SHORT).show();
        }else {
            if (Objects.equals(tipo,"pasar")){
                AdaptadorAsistencia adaptadorAsistencia = new AdaptadorAsistencia(c,asistenciaCAs,tipo,a);
                estudiantes.setLayoutManager(new LinearLayoutManager(c));
                estudiantes.setAdapter(adaptadorAsistencia);
                estudiantes.addItemDecoration(new DividerItemDecoration(c,DividerItemDecoration.VERTICAL));

                ItemTouchHelperExtension.Callback mCallback = new ItemTouchHelperCallbackAsistencia();
                ItemTouchHelperExtension mItemTouchHelper = new ItemTouchHelperExtension(mCallback);
                mItemTouchHelper.attachToRecyclerView(estudiantes);
                adaptadorAsistencia.setItemTouchHelperExtension(mItemTouchHelper);
            }else {
                AdaptadorAsistencia adaptadorAsistencia = new AdaptadorAsistencia(c,asistenciaCAs,tipo,a);
                estudiantes.setLayoutManager(new LinearLayoutManager(c));
                estudiantes.addItemDecoration(new DividerItemDecoration(c,DividerItemDecoration.VERTICAL));
                estudiantes.setAdapter(adaptadorAsistencia);
            }
        }
    }
}
