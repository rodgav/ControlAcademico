package com.nationfis.controlacademicononfc.Clases.Spinners.Sede;

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
 * Created by Sam on 19/08/2017.
 */

public class AnalizadorSede extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    @SuppressLint("StaticFieldLeak")
    private Spinner sedes;
    private ArrayList<String> ids = new ArrayList<>();
    private ArrayList<String>nos = new ArrayList<>();
    private DatosDatos datosDatos;
    AnalizadorSede(Context c, String s, Spinner sedes) {
        this.c = c;
        this.s = s;
        this.sedes = sedes;
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
            ArrayAdapter<String> a = new ArrayAdapter<>(c,android.R.layout.simple_list_item_1,nos);
            sedes.setAdapter(a);
            sedes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(c,nos.get(i),Toast.LENGTH_SHORT).show();
                    datosDatos = new DatosDatos();
                    datosDatos.setSedes(ids.get(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
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
                String codigo = jo.getString("codigo");
                ids.add(codigo);
                nos.add(nombre);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
