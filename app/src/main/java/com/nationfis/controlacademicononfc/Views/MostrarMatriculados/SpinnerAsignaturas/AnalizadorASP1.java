package com.nationfis.controlacademicononfc.Views.MostrarMatriculados.SpinnerAsignaturas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.R;
import com.nationfis.controlacademicononfc.Views.MostrarMatriculados.EnviarAsistencia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/*
 * Created by GlobalTIC's on 7/03/2018.
 */

public class AnalizadorASP1 extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    @SuppressLint("StaticFieldLeak")
    private Spinner asignaturas;
    private String s;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView estudiantes,estudiantesa;
    private SharedPreferences preferences;
    private ArrayList<String> noa = new ArrayList<>();
    private ArrayList<Integer> ida = new ArrayList<>();
    AnalizadorASP1(Context c, String s, Spinner asignaturas, RecyclerView estudiantes, RecyclerView estudiantesa) {
        this.c = c;
        this.s = s;
        this.asignaturas = asignaturas;
        this.estudiantes = estudiantes;
        this.estudiantesa = estudiantesa;
        preferences = c.getSharedPreferences("datos",Context.MODE_PRIVATE);
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
            Toast.makeText(c,"No se pudo analizar",Toast.LENGTH_SHORT).show();
        }else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(c,android.R.layout.simple_list_item_1,noa);
            asignaturas.setAdapter(adapter);
            asignaturas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String accion1 = MD5.encrypt("estudiantesn");
                    String accion2 = MD5.encrypt("estudiantesa");
                    String anioa = c.getResources().getString(R.string.año);
                    Integer sede = preferences.getInt("sede",0);

                    new EnviarAsistencia(c,urla,ida.get(i),estudiantes,accion1,sede,anioa).execute();

                    new EnviarAsistencia(c,urla,ida.get(i),estudiantesa,accion2,sede,anioa).execute();
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
                Integer codigo = jo.getInt("codigo");
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


