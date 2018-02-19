package com.nationfis.controlacademicononfc.Clases.ActualizarHorario;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.TAG;

/*
 * Created by GlobalTIC's on 7/02/2018.
 */

public class AnalizarActualizarHorario extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s,nombredia,mensaje,inicio1,fin1;
    @SuppressLint("StaticFieldLeak")
    private TextView dia,inicio,fin;
    AnalizarActualizarHorario(Context c, String s, TextView dia, TextView inicio, TextView fin, String nombredia,
                              String inicio1, String fin1) {
        this.c = c;
        this.s = s;
        this.dia = dia;
        this.inicio = inicio;
        this.fin = fin;
        this.nombredia = nombredia;
        this.inicio1 = inicio1;
        this.fin1 = fin1;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No se pudo analizar",Toast.LENGTH_SHORT).show();
        }else {
            if (Objects.equals(mensaje,"Horario Actualizado")){
                dia.setText(nombredia);
                inicio.setText(inicio1);
                fin.setText(fin1);
            }
            Toast.makeText(c,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    private Integer analizar() {
        try {
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            for (int i = 0; i<ja.length();i++){
                jo=ja.getJSONObject(i);
                mensaje = jo.getString("mensaje");
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w(TAG,s);
        }
        return 0;
    }
}
