package com.nationfis.controlacademicononfc.Clases.ActualizarAsistencia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/*
 * Created by Sam on 26/04/2017.
 */

public class AnalizarComprobarAsistencia extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s,asistio;
    private Dialog d;
    @SuppressLint("StaticFieldLeak")
    private TextView asisti;
    private String mensaje;
    AnalizarComprobarAsistencia(Context c, String s, Dialog d, TextView asisti, String asistio) {
        this.c=c;
        this.s = s;
        this.d = d;
        this.asisti = asisti;
        this.asistio = asistio;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No se pudo analizar",Toast.LENGTH_SHORT).show();
        }else {
            if (Objects.equals(mensaje,"Asistencia Actualizada")){
                String asi = "asistio";
                String falt = "falto";
                if (Objects.equals(asistio,"0")){
                    asisti.setText(falt);
                }else if (Objects.equals(asistio,"1")){
                    asisti.setText(asi);
                }
                d.dismiss();
            }
            Toast.makeText(c,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analaizador();
    }

    private Integer analaizador() {
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
        }
        return 0;
    }
}
