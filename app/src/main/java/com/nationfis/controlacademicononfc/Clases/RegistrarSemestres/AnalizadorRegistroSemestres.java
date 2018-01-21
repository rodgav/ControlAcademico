package com.nationfis.controlacademicononfc.Clases.RegistrarSemestres;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Created by Sam on 04/05/2017.
 */

public class AnalizadorRegistroSemestres extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    private ArrayList<String>mensaje1=new ArrayList<>();
    AnalizadorRegistroSemestres(Context c, String s) {
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
            Toast.makeText(c,"No se pudo registrar",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(c,mensaje1.get(0),Toast.LENGTH_SHORT).show();
        }
    }

    private Integer analizar() {

        try {
            mensaje1.clear();
            JSONArray ja = new JSONArray(s);
            JSONObject jo;
            for (int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String mensaje = jo.getString("mensaje");
                mensaje1.add(mensaje);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
