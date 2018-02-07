package com.nationfis.controlacademicononfc.Clases.RegistrarAsignaturasDocentes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.TAG;


/*
 * Created by Sam on 02/05/2017.
 */

public class AnalizarRegistroAsignaturaDocentes extends AsyncTask<Void,Void,Integer>{
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    private String mensaje;
    private Dialog d;
    AnalizarRegistroAsignaturaDocentes(Context c, String s, Dialog d) {
        this.c = c;
        this.s = s;
        this.d = d;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No se pudo registrar",Toast.LENGTH_SHORT).show();
        }else {
            if (mensaje.length()>0){
                Toast.makeText(c,mensaje,Toast.LENGTH_LONG).show();
                d.dismiss();
            }
        }
    }

    private Integer analizar() {
        try {
            JSONArray ja = new JSONArray(s);
            JSONObject jo;
            for (int i=0;i<ja.length();i++){
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
