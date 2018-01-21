package com.nationfis.controlacademicononfc.Clases.RegistrarMatricula;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Created by SamGM on 22/04/2017.
 */

public class AnalizadorMatricula extends AsyncTask<Void,Void,Integer>{
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    private ArrayList<String>mensaje = new ArrayList<>();
    AnalizadorMatricula(Context c, String s) {
        this.s = s;
        this.c = c;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {

        try {
            mensaje.clear();
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            for (int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);

                String mensaje1 = jo.getString("mensaje");
                mensaje.add(mensaje1);
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
        if (integer==1){
            Toast.makeText(c,mensaje.get(0),Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(c,"Error en la matricula",Toast.LENGTH_SHORT).show();
        }
    }
}
