package com.nationfis.controlacademicononfc.Clases.Spinners.Unidad;

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

/**
 * Created by Sam on 27/05/2017.
 */

public class AnalizadorUnidades extends AsyncTask<Void,Void,Integer> {
    private Context c;
    private String s;
    private Spinner unidad;
    private ArrayList<String> idu = new ArrayList<>();
    private ArrayList<String>nou = new ArrayList<>();
    private DatosDatos datosDatos;
    public AnalizadorUnidades(Context c, String s, Spinner unidad) {
        this.c = c;
        this.s = s;
        this.unidad = unidad;
        this.datosDatos = new DatosDatos();
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
            ArrayAdapter<String> a = new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,nou);
            unidad.setAdapter(a);
            unidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(c,nou.get(i),Toast.LENGTH_SHORT).show();
                    datosDatos.setUnidades(idu.get(i));
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
            idu.clear();
            nou.clear();
            for (int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String nombre = jo.getString("nombre");
                String codigo = jo.getString("codigo");
                idu.add(codigo);
                nou.add(nombre);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
