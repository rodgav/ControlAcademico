package com.nationfis.controlacademicononfc.Clases.Spinners.Distritos;

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

public class AnalizadorDistritos extends AsyncTask<Void,Void,Integer>{
    private ArrayList<String> nod = new ArrayList<>();
    private ArrayList<Integer>idd = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    @SuppressLint("StaticFieldLeak")
    private Spinner distrito;
    AnalizadorDistritos(Context c, Spinner distrito, String s) {
        this.c = c;
        this.distrito = distrito;
        this.s = s;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer ==0){
            Toast.makeText(c,"No se pudo analizar los datos",Toast.LENGTH_SHORT).show();
        }else {
            ArrayAdapter<String> a = new ArrayAdapter<>(c,android.R.layout.simple_expandable_list_item_1,nod);
            distrito.setAdapter(a);

            distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                    //Toast.makeText(c,nod.get(i),Toast.LENGTH_SHORT).show();
                    DatosDatos datosDatos = new DatosDatos();
                    datosDatos.setDistrito(idd.get(i));

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }
    private Integer analizar() {
        try{
            JSONArray ja = new JSONArray(s);
            JSONObject jo;
            nod.clear();
            idd.clear();
            for (int i=0;i<ja.length();i++){
                jo = ja.getJSONObject(i);
                Integer id = jo.getInt("codigo");
                String nombre = jo.getString("nombre");

                nod.add(nombre);
                idd.add(id);
            }
            return 1;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return 0;
    }
}
