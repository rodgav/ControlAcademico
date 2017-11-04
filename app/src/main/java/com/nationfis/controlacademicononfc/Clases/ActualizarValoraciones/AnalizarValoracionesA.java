package com.nationfis.controlacademicononfc.Clases.ActualizarValoraciones;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by Sam on 18/06/2017.
 */

public class AnalizarValoracionesA extends AsyncTask<Void,Void,Integer> {
    private Context c;
    private  String s,peso2;
    private Dialog d;
    private TextView peso;
    ArrayList<String> mensaje1 = new ArrayList<>();
    public AnalizarValoracionesA(Context c, String s, Dialog d, TextView peso, String peso2) {
        this.c = c;
        this.s = s;
        this.d = d;
        this.peso = peso;
        this.peso2 = peso2;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No se pudo analizar",Toast.LENGTH_SHORT).show();
        }else {
            if (Objects.equals(mensaje1.get(0),"Valoracion Actualizada")){
                peso.setText(peso2);
                d.dismiss();
            }
            Toast.makeText(c,mensaje1.get(0),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        try {
            mensaje1.clear();
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            for (int i = 0; i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String mensaje = jo.getString("mensaje");
                mensaje1.add(mensaje);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
