package com.nationfis.controlacademicononfc.Clases.ActualizarAsignaturasDoc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/*
 * Created by GlobalTIC's on 7/02/2018.
 */

public class AnalizarActualizarAsignaturaDoc extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s,nombrea,mensaje;
    @SuppressLint("StaticFieldLeak")
    private TextView asignatura;
    AnalizarActualizarAsignaturaDoc(Context c, String s, TextView asignatura, String nombrea) {
        this.c = c;
        this.s = s;
        this.asignatura = asignatura;
        this.nombrea = nombrea;
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
            if (Objects.equals(mensaje,"Asignatura Docente Actualizada")){
                asignatura.setText(nombrea);
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
        }
        return 0;
    }
}
