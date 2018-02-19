package com.nationfis.controlacademicononfc.Views.MostrarHorarios;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.TAG;


/*
 * Created by GlobalTIC's on 13/02/2018.
 */

public class AnalizarHorarios extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView horario;
    private ArrayList<Horarios> horarios = new ArrayList<>();
    AnalizarHorarios(Context c, String s, RecyclerView horario) {
        this.c = c;
        this.s = s;
        this.horario = horario;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer!=0){
            AdaptadorHorario adaptadorHorario = new AdaptadorHorario(c,horarios);
            horario.setAdapter(adaptadorHorario);

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
            horarios.clear();
            Horarios horarios1;

            for (int i = 0; i<ja.length();i++){
                jo=ja.getJSONObject(i);
                /*String nombre = jo.getString("nombre");
                String nombrec = jo.getString("nombrec");
                String inicio = ]jo.getString("inicio");
                String fin = jo.getString("fin");

                horarios1 = new Horarios();

                horarios1.setNombre(nombre);
                horarios1.setNombrec(nombrec);
                horarios1.setInicio(inicio);
                horarios1.setFin(fin);

                horarios.add(horarios1);*/
                horarios1 = new Horarios();
                switch (jo.getString("inicio")){
                    case "08:30:00":
                        horarios1.setNombre(jo.getString("nombre"));
                        horarios1.setNombrec(jo.getString("nombrec"));
                        horarios1.setInicio(jo.getString("inicio"));
                        horarios1.setFin(jo.getString("fin"));
                        break;
                    case "10:30:00":
                        horarios1.setNombre(jo.getString("nombre"));
                        horarios1.setNombrec(jo.getString("nombrec"));
                        horarios1.setInicio(jo.getString("inicio"));
                        horarios1.setFin(jo.getString("fin"));
                        break;
                }

                horarios.add(horarios1);
            }
            Log.w(TAG,s);
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
