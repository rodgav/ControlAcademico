package com.nationfis.controlacademicononfc.Clases.EliminarToken;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.TAG;

/*
 * Created by GlobalTIC's on 31/01/2018.
 */

public class AnalizarEliminarToken extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s,mensaje;
    AnalizarEliminarToken(Context c, String s) {
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
        if(integer==1) {
            if (mensaje != null) {
                Toast.makeText(c, mensaje, Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = c.getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear().apply();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(intent);
                ((Activity) (c)).finish();
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
    }
        return 0;
    }
}
