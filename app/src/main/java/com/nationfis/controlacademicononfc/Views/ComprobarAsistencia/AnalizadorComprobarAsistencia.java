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

    AnalizadorComprobarAsistencia(Context c, String s, RecyclerView estudiantes, String tipo) {
        this.c = c;
        this.s = s;
        this.estudiantes = estudiantes;
        this.tipo = tipo;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        try {
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            AsistenciaCA asistenciaCA;
            asistenciaCAs.clear();
            for (int i = 0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String nombre = jo.getString("nombre");
                String codigo = jo.getString("codigo");
                String foto = jo.getString("foto");
                String asistio = jo.getString("asistio");

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
        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No se cargo la asistencia",Toast.LENGTH_SHORT).show();
        }else {
            AdaptadorAsistencia a = new AdaptadorAsistencia(c,asistenciaCAs,tipo);
            estudiantes.setAdapter(a);

            estudiantes.setLayoutManager(new LinearLayoutManager(c));

            estudiantes.setAdapter(a);
            estudiantes.addItemDecoration(new DividerItemDecoration(c,DividerItemDecoration.VERTICAL));


            ItemTouchHelperExtension.Callback mCallback = new ItemTouchHelperCallbackAsistencia();

            ItemTouchHelperExtension mItemTouchHelper = new ItemTouchHelperExtension(mCallback);
            mItemTouchHelper.attachToRecyclerView(estudiantes);

            a.setItemTouchHelperExtension(mItemTouchHelper);
        }

    }
}
