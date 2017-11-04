package com.nationfis.controlacademicononfc.Clases.Spinners.AsignaturasEstudiantes;

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
 * Created by Sam on 11/06/2017.
 */

public class AnalizarAsignaturasEstudiantes extends AsyncTask<Void,Void,Integer>{
    private String s;
    private Spinner asignaturas;
    private Context c;
    ArrayList<String> noa = new ArrayList<>();
    ArrayList<String>ida = new ArrayList<>();
    private DatosDatos datosDatos;
    public AnalizarAsignaturasEstudiantes(Context c, String s, Spinner asignaturas) {
        this.s = s;
        this.c = c;
        this.asignaturas = asignaturas;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer == 0){
            Toast.makeText(c,"No se pudo analizar",Toast.LENGTH_SHORT).show();
        }else {
            ArrayAdapter<String> a = new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,noa);
            asignaturas.setAdapter(a);
            datosDatos = new DatosDatos();
            asignaturas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(c,noa.get(i),Toast.LENGTH_SHORT).show();
                    datosDatos.setAsignaturase(ida.get(i));
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
                String nombrea = jo.getString("nombrea");
                String codigo = jo.getString("ida");
                ida.add(codigo);
                noa.add(nombrea);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
