package com.nationfis.controlacademicononfc.Clases.EnviarAsistencia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * Created by SamGM on 23/04/2017.
 */

public class AnalizarAsistencia extends AsyncTask<Void,Void,Integer>{
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    private String mensaje;
    AnalizarAsistencia(Context c, String s) {
        this.c=c;
        this.s=s;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        try {
            JSONObject jo;
            JSONArray ja = new JSONArray(s);

            for (int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                mensaje = jo.getString("mensaje");
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
            Toast.makeText(c,"No pasado",Toast.LENGTH_SHORT).show();
        }else {
            if (mensaje!=null){
                Toast.makeText(c, mensaje,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
