package com.nationfis.controlacademicononfc.Clases.Spinners.AsignaturasDocentes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
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


/*
 * Created by SamGM on 22/04/2017.
 */

public class AnalizadorAsignaturasDocentes extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    @SuppressLint("StaticFieldLeak")
    private Spinner asignaturas;
    private String s;
    @SuppressLint("StaticFieldLeak")
    private ArrayList<String> noa = new ArrayList<>();
    private ArrayList<String> ida = new ArrayList<>();
    private DatosDatos datosDatos = new DatosDatos();
    AnalizadorAsignaturasDocentes(Context c, String s, Spinner asignaturas) {
        this.c = c;
        this.s = s;
        this.asignaturas = asignaturas;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer ==0){
            Toast.makeText(c,"Nose pudo analizar",Toast.LENGTH_SHORT).show();
        }else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(c,android.R.layout.simple_list_item_1,noa);
            asignaturas.setAdapter(adapter);
            asignaturas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //Toast.makeText(c,ida.get(i),Toast.LENGTH_SHORT).show();
                    String asignaturasd = ida.get(i);
                    datosDatos.setAsignaturasd(asignaturasd);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private Integer analizar() {
        try {
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            noa.clear();
            ida.clear();
            for (int i = 0; i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String codigo=jo.getString("codigo");
                String nombre = jo.getString("nombre");
                noa.add(nombre);
                ida.add(codigo);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
