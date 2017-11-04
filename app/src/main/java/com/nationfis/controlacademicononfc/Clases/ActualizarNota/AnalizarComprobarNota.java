package com.nationfis.controlacademicononfc.Clases.ActualizarNota;

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
 * Created by Sam on 06/06/2017.
 */

public class AnalizarComprobarNota extends AsyncTask<Void,Void,Integer>{
    private Context c;
    private Dialog d;
    private String s,nota2;
    private TextView nota;
    ArrayList<String> mensaje1 = new ArrayList<>();
    public AnalizarComprobarNota(Context c, String s, Dialog d, TextView nota,String nota2) {
        this.c=c;
        this.s = s;
        this.d = d;
        this.nota = nota;
        this.nota2 = nota2;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No se pudo analizar",Toast.LENGTH_SHORT).show();
        }else {
            if (Objects.equals(mensaje1.get(0),"Nota Actualizada")){
                nota.setText(nota2);
                d.dismiss();
            }
            Toast.makeText(c,mensaje1.get(0),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizador();
    }

    private Integer analizador() {
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
