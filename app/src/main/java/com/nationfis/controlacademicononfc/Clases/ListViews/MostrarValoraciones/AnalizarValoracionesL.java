package com.nationfis.controlacademicononfc.Clases.ListViews.MostrarValoraciones;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Created by Sam on 18/06/2017.
 */

public class AnalizarValoracionesL extends AsyncTask<Void,Void,Integer>{
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    @SuppressLint("StaticFieldLeak")
    private ListView valores;
    private ArrayList<ValoracionesL> valoracionesLs = new ArrayList<>();
    AnalizarValoracionesL(Context c, String s, ListView valores) {
        this.c = c;
        this.s = s;
        this.valores = valores;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        try {
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            ValoracionesL valoracionesL;
            for (int i = 0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String codigo = jo.getString("codigo");
                String nombre = jo.getString("nombre");
                String peso = jo.getString("peso");
                String nombrea = jo.getString("nombrea");
                String nombreu = jo.getString("nombreu");

                valoracionesL = new ValoracionesL();
                valoracionesL.setCodigo(codigo);
                valoracionesL.setNombre(nombre);
                valoracionesL.setPeso(peso);
                valoracionesL.setNombrea(nombrea);
                valoracionesL.setNombreu(nombreu);
                valoracionesLs.add(valoracionesL);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No se pudo cargar los matriculados",Toast.LENGTH_SHORT).show();
        }else {
            AdaptadorValoracionesL a = new AdaptadorValoracionesL(c,valoracionesLs);
            valores.setAdapter(a);
            Helper.getListViewSize(valores);
        }
    }
}
