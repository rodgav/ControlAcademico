package com.nationfis.controlacademicononfc.Clases.Spinners.TipoMatricula;

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
 * Created by SamGM on 23/04/2017.
 */

public class AnalizadorTipoMatricula extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    @SuppressLint("StaticFieldLeak")
    private Spinner tipom;
    private ArrayList<Integer>idm = new ArrayList<>();
    private ArrayList<String>nom = new ArrayList<>();
    private DatosDatos datosDatos;
    AnalizadorTipoMatricula(Context c, String s, Spinner tipom) {
        this.c = c;
        this.s = s;
        this.tipom = tipom;
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
            ArrayAdapter<String> a = new ArrayAdapter<>(c,android.R.layout.simple_list_item_1,nom);
            tipom.setAdapter(a);
            tipom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //Toast.makeText(c,nom.get(i),Toast.LENGTH_SHORT).show();
                    datosDatos = new DatosDatos();
                    datosDatos.setTiposm(idm.get(i));
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
            idm.clear();
            nom.clear();
            for (int i=1;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String nombre = jo.getString("nombre");
                Integer codigo = jo.getInt("codigo");
                idm.add(codigo);
                nom.add(nombre);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
