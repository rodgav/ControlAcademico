package com.nationfis.controlacademicononfc.Clases.RegistrarHorario;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.TAG;

/*
 * Created by GlobalTIC's on 5/02/2018.
 */

public class AnalizarRegistrarHorario extends AsyncTask<Void, Void, Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    private ArrayList<String> mensaje = new ArrayList<>();
    private Dialog d;

    AnalizarRegistrarHorario(Context c, String s) {
        this.c = c;
        this.s = s;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer == 0) {
            Toast.makeText(c, "No se pudo registrar", Toast.LENGTH_SHORT).show();
        } else {
            for (String elemento : mensaje) {
                Toast.makeText(c, elemento, Toast.LENGTH_LONG).show();
            }
        }
    }

    private Integer analizar() {
        try {
            JSONArray ja = new JSONArray(s);
            JSONObject jo;
            mensaje.clear();
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                String mensaje0 = jo.getString("mensaje");
                mensaje.add(mensaje0);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w(TAG,s);
        }
        return 0;
    }
}
