package com.nationfis.controlacademicononfc.Clases.EnviarAsistencia;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Clases.ListViews.ComprobarAsistencia.ComprobarAsistencia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by SamGM on 23/04/2017.
 */

public class AnalizarAsistencia extends AsyncTask<Void,Void,Integer>{
    private Context c;
    private String s;
    ArrayList<String>mensajes=new ArrayList<>();
    public AnalizarAsistencia(Context c, String s) {
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

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No pasado",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(c, mensajes.get(0),Toast.LENGTH_SHORT).show();
        }
    }
}
