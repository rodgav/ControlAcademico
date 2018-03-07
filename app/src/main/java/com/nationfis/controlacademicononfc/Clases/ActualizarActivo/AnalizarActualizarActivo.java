package com.nationfis.controlacademicononfc.Clases.ActualizarActivo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/*
 * Created by Sam on 20/08/2017.
 */

public class AnalizarActualizarActivo extends AsyncTask<Void,Void,Integer> {
    private String mensaje,s;
    @SuppressLint("StaticFieldLeak")
    private Context c;
    @SuppressLint("StaticFieldLeak")
    private TextView activado;
    private Integer activoid;
    AnalizarActualizarActivo(Context c, String s, TextView activado, Integer activoid) {
        this.c = c;
        this.s = s;
        this.activado = activado;
        this.activoid = activoid;
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
            if (Objects.equals(mensaje,"Accion Realizada")){
                String asi = "activado";
                String falt = "inactivo";
                if (Objects.equals(activoid,0)){
                    activado.setText(falt);
                    activado.setTextColor(ContextCompat.getColor(c,R.color.inactivo));
                }else if (Objects.equals(activoid,1)){
                    activado.setText(asi);
                    activado.setTextColor(ContextCompat.getColor(c, R.color.activo));
                }
            }
            Toast.makeText(c,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    private Integer analizar() {
        try {
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            for (int i = 0; i<ja.length();i++){
                jo=ja.getJSONObject(i);
                mensaje = jo.getString("mensaje");
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
