package com.nationfis.controlacademicononfc.Clases.ActualizarAsistencia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


/*
 * Created by Sam on 26/04/2017.
 */

public class AnalizarComprobarAsistencia extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    private Integer asistio;
    @SuppressLint("StaticFieldLeak")
    private TextView asisti;
    private String mensaje;
    @SuppressLint("StaticFieldLeak")
    private ImageView act;
    AnalizarComprobarAsistencia(Context c, String s, TextView asisti, Integer asistio, ImageView act) {
        this.c=c;
        this.s = s;
        this.asisti = asisti;
        this.asistio = asistio;
        this.act = act;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No se pudo analizar",Toast.LENGTH_SHORT).show();
        }else {
            if (Objects.equals(mensaje,"Asistencia Actualizada")){
                String asi = "asistio";
                String falt = "falto";
                if (Objects.equals(asistio,0)){
                    asisti.setText(falt);
                    act.setImageResource(R.mipmap.error);
                }else if (Objects.equals(asistio,1)){
                    asisti.setText(asi);
                    act.setImageResource(R.mipmap.check);
                }
            }
            Toast.makeText(c,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analaizador();
    }

    private Integer analaizador() {
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
