package com.nationfis.controlacademicononfc.Views.ComprobarNotas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.TAG;

/*
 * Created by Sam on 06/06/2017.
 */

public class AnalizadorComprobarNotas extends AsyncTask<Void, Void, Integer> {
    private String s, tipo;
    @SuppressLint("StaticFieldLeak")
    private Context c;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView notas;
    private ArrayList<NotasCN> notaCNs = new ArrayList<>();

    AnalizadorComprobarNotas(Context c, String s, RecyclerView notas, String tipo) {
        this.c = c;
        this.s = s;
        this.notas = notas;
        this.tipo = tipo;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer == 0) {
            Toast.makeText(c, "No se cargaron las notas", Toast.LENGTH_SHORT).show();
        } else {
            AdaptadorNotas a = new AdaptadorNotas(c, notaCNs, tipo);
            notas.setAdapter(a);
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        try {
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            NotasCN notasCN;
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                String nombre = jo.getString("nombre");
                Integer codigo = jo.getInt("codigo");
                String foto = jo.getString("foto");
                Integer nota = jo.getInt("nota");

                notasCN = new NotasCN();
                notasCN.setNombre(nombre);
                notasCN.setCodigo(codigo);
                notasCN.setFoto(foto);
                notasCN.setNota(nota);
                notaCNs.add(notasCN);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
