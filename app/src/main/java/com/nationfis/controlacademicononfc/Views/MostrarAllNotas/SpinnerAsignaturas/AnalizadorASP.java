package com.nationfis.controlacademicononfc.Views.MostrarAllNotas.SpinnerAsignaturas;

/*
 * Created by GlobalTIC's on 25/02/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.R;
import com.nationfis.controlacademicononfc.Views.MostrarAllNotas.RecibirNotas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

public class AnalizadorASP extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    @SuppressLint("StaticFieldLeak")
    private Spinner asignaturas;
    private String s;
    @SuppressLint("StaticFieldLeak")
    private TableLayout notas;
    private ArrayList<String> noa = new ArrayList<>();
    private ArrayList<Integer> ida = new ArrayList<>();
    AnalizadorASP(Context c, String s, Spinner asignaturas, TableLayout notas) {
        this.c = c;
        this.s = s;
        this.asignaturas = asignaturas;
        this.notas = notas;
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
                    Integer asignaturasd = ida.get(i);
                    String accion = MD5.encrypt("mnotad");
                    String anioa = c.getResources().getString(R.string.año);
                    new RecibirNotas(c,urla,accion,anioa,asignaturasd,notas).execute();
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

