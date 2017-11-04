package com.nationfis.controlacademicononfc.Clases.Spinners.Asignaturas;

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
 * Created by SamGM on 22/04/2017.
 */

public class AnalizadorAsignaturas extends AsyncTask<Void,Void,Integer>{
    private String s;
    private Spinner asignatura;
    private Context c;
    ArrayList<String>noa = new ArrayList<>();
    ArrayList<String>ida = new ArrayList<>();
    private DatosDatos datosDatos;
    public AnalizadorAsignaturas(Context c, String s, Spinner asignatura) {
        this.s = s;
        this.c = c;
        this.asignatura = asignatura;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer == 0){
            Toast.makeText(c,"No se pudo analizar",Toast.LENGTH_SHORT).show();
        }else {
            ArrayAdapter<String> a = new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,noa);
            asignatura.setAdapter(a);
            datosDatos = new DatosDatos();
            asignatura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(c,noa.get(i),Toast.LENGTH_SHORT).show();
                    datosDatos.setAsignaturas(ida.get(i));
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizador();
    }

    private Integer analizador() {
        try {
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            noa.clear();
            ida.clear();
            for (int i =0; i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String nombre = jo.getString("nombre");
                String codigo = jo.getString("codigo");
                ida.add(codigo);
                noa.add(nombre);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
