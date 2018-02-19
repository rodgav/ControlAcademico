package com.nationfis.controlacademicononfc.Clases.EliminarHorario;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Views.MostrarEstudiantes.Estudiantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.TAG;

/*
 * Created by GlobalTIC's on 7/02/2018.
 */

public class AnalizarEliminarHorario extends AsyncTask<Void,Void,Integer>{
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s,mensaje;
    private ArrayList<Estudiantes>estudiantes;
    private int adapterPosition;
    private Dialog d;

    AnalizarEliminarHorario(Context c, String s, ArrayList<Estudiantes> estudiantes, int adapterPosition, Dialog d) {
        this.c = c;
        this.s = s;
        this.estudiantes = estudiantes;
        this.adapterPosition = adapterPosition;
        this.d = d;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer==1) {
            if (mensaje != null) {
                Toast.makeText(c, mensaje, Toast.LENGTH_SHORT).show();
                d.dismiss();
                estudiantes.remove(adapterPosition);
            }
        }
    }

    private Integer analizar() {try{
        JSONArray ja = new JSONArray(s);
        JSONObject jo;
        for(int i=0;i<ja.length();i++){
            jo=ja.getJSONObject(i);
            mensaje=jo.getString("mensaje");
        }
        return 1;
    }catch (JSONException e){
        e.printStackTrace();
        Log.w(TAG,s);
    }
        return 0;
    }
}
