package com.nationfis.controlacademicononfc.Clases.Spinners.Valoraciones;

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
 * Created by Sam on 28/05/2017.
 */

public class AnalizadorValoraciones extends AsyncTask<Void,Void,Integer>{
    private Context c;
    private String s;
    private Spinner valores;
    private ArrayList<String> idv = new ArrayList<>();
    private ArrayList<String> nov = new ArrayList<>();
    private DatosDatos datosDatos;
    public AnalizadorValoraciones(Context c, String s, Spinner valores) {
        this.c = c;
        this.s = s;
        this.valores = valores;
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
            ArrayAdapter<String> a = new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,nov);
            valores.setAdapter(a);
            valores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(c,nov.get(i),Toast.LENGTH_SHORT).show();
                    datosDatos.setValoraciones(idv.get(i));
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
            idv.clear();
            nov.clear();
            for (int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String nombre = jo.getString("nombre");
                String codigo = jo.getString("codigo");
                idv.add(codigo);
                nov.add(nombre);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
