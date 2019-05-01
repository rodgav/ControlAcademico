package com.nationfis.controlacademicononfc.Clases.Spinners.Asignaturas;

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
 * Created by SamGM on 22/04/2017.
 */

public class AnalizadorAsignaturas extends AsyncTask<Void,Void,Integer>{
    private String s;
    @SuppressLint("StaticFieldLeak")
    private Spinner asignatura;
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private ArrayList<String>noa = new ArrayList<>();
    private ArrayList<Integer>ida = new ArrayList<>();
    private DatosDatos datosDatos;
    private int idar,posicion;
    AnalizadorAsignaturas(Context c, String s, Spinner asignatura, int idar) {
        this.s = s;
        this.c = c;
        this.asignatura = asignatura;
        this.idar = idar;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer == 0){
            Toast.makeText(c,"No se pudo analizar",Toast.LENGTH_SHORT).show();
        }else {
            ArrayAdapter<String> a = new ArrayAdapter<>(c,android.R.layout.simple_list_item_1,noa);
            asignatura.setAdapter(a);
            datosDatos = new DatosDatos();
            asignatura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //Toast.makeText(c,noa.get(i),Toast.LENGTH_SHORT).show();
                    datosDatos.setAsignaturas(ida.get(i));
                    datosDatos.setAsignaturasNombre(noa.get(i));
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            if (posicion>=0){
                asignatura.setSelection(posicion);
            }
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
                int codigo = jo.getInt("codigo");

                if (idar==codigo){
                    posicion=i;
                }

                ida.add(codigo);
                noa.add(nombre);
            }
            Log.w(TAG,s);
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
