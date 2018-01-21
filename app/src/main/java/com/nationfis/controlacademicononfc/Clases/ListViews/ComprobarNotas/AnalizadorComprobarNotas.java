package com.nationfis.controlacademicononfc.Clases.ListViews.ComprobarNotas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/*
 * Created by Sam on 06/06/2017.
 */

public class AnalizadorComprobarNotas extends AsyncTask<Void,Void,Integer>{
    private String s,tipo;
    @SuppressLint("StaticFieldLeak")
    private Context c;
    @SuppressLint("StaticFieldLeak")
    private ListView notas;
    private ArrayList<NotasCN> notaCNs = new ArrayList<>();
    AnalizadorComprobarNotas(Context c, String s, ListView notas, String tipo) {
        this.c = c;
        this.s = s;
        this.notas = notas;
        this.tipo = tipo;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No se cargaron las notas",Toast.LENGTH_SHORT).show();
        }else {
            AdaptadorComprobarNotas a = new AdaptadorComprobarNotas(c,notaCNs,tipo);
            notas.setAdapter(a);
            Helper.getListViewSize(notas);
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        if (Objects.equals(tipo,"doc")){
            try {
                JSONObject jo;
                JSONArray ja = new JSONArray(s);
                NotasCN notasCN;
                for (int i = 0;i<ja.length();i++){
                    jo=ja.getJSONObject(i);
                    String nombre = jo.getString("nombre");
                    String codigo = jo.getString("codigo");
                    String foto = jo.getString("foto");
                    String nota = jo.getString("nota");

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
            }return 0;
        }else if (Objects.equals(tipo,"est")){
            try {
                JSONObject jo;
                JSONArray ja = new JSONArray(s);
                NotasCN notasCN;
                for (int i = 0;i<ja.length();i++){
                    jo=ja.getJSONObject(i);
                    String nombre = jo.getString("nombre");
                    String codigo = jo.getString("codigo");
                    String foto = jo.getString("foto");
                    String nota = jo.getString("nota");
                    String peso = jo.getString("peso");

                    notasCN = new NotasCN();
                    notasCN.setNombre(nombre);
                    notasCN.setCodigo(codigo);
                    notasCN.setFoto(foto);
                    notasCN.setNota(nota);
                    notasCN.setPeso(peso);
                    notaCNs.add(notasCN);
                }
                return 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }return 0;
        }
        return 0;
    }
}
