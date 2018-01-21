package com.nationfis.controlacademicononfc.Clases.EnviarNotas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Created by Sam on 29/05/2017.
 */

public class AnalizarNota extends AsyncTask<Void,Void,Integer>{
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    private ArrayList<String> mensajes=new ArrayList<>();
    AnalizarNota(Context c, String s) {
        this.c = c;
        this.s = s;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No pasado",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(c, mensajes.get(0),Toast.LENGTH_SHORT).show();
        }
    }

    private Integer analizar() {
        try {
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            mensajes.clear();
            for (int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String mensaje = jo.getString("mensaje");
                mensajes.add(mensaje);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
