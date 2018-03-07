package com.nationfis.controlacademicononfc.Clases.Spinners.Semana;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Clases.DatosDatos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.TAG;

/*
 * Created by GlobalTIC's on 5/02/2018.
 */

public class AnalizadorSemana extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    @SuppressLint("StaticFieldLeak")
    private Spinner semana;
    private int idsr,posicion;

    AnalizadorSemana(Context c, String s, Spinner semana,int idsr) {
        this.c = c;
        this.s = s;
        this.semana = semana;
        this.idsr = idsr;
    }

    private ArrayList<Integer> ids = new ArrayList<>();
    private ArrayList<String> nos = new ArrayList<>();
    private DatosDatos datosDatos;
    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        try {
            JSONArray ja = new JSONArray(s);
            JSONObject jo;
            ids.clear();
            nos.clear();
            for (int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String nombre = jo.getString("nombre");
                Integer codigo = jo.getInt("codigo");

                if (idsr==codigo){
                    posicion=i;
                }

                ids.add(codigo);
                nos.add(nombre);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w(TAG,s);
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No se pudo analizar",Toast.LENGTH_SHORT).show();
        }else {
            ArrayAdapter<String> a = new ArrayAdapter<>(c,android.R.layout.simple_list_item_1,nos);
            semana.setAdapter(a);
            semana.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    datosDatos = new DatosDatos();
                    datosDatos.setSemana(ids.get(i));
                    datosDatos.setNombreDia(nos.get(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            if (posicion>=0){
                semana.setSelection(posicion);
            }
        }
    }
}
