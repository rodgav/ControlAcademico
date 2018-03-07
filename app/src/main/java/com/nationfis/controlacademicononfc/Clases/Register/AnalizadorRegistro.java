package com.nationfis.controlacademicononfc.Clases.Register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.nationfis.controlacademicononfc.Clases.Login.ComprobarLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.TAG;
import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/*
 * Created by SamGM on 14/04/2017.
 */

class AnalizadorRegistro extends AsyncTask<Void,Void,Integer>{
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s,contraseña1;
    @SuppressLint("StaticFieldLeak")
    private String mensaje;
    private Integer codigo1;
    AnalizadorRegistro(Context c, String s, Integer codigo1, String contraseña1) {
        this.s = s;
        this.c=c;
        this.codigo1 = codigo1;
        this.contraseña1 = contraseña1;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer==1){
            //String urla = "http://nationfis.hol.es/nonfc/login.php";
            Toast.makeText(c,mensaje,Toast.LENGTH_SHORT).show();
            String TOKEN = FirebaseInstanceId.getInstance().getToken();
            if (TOKEN != null){
                if (TOKEN.contains("{")){
                    try{
                        JSONObject jo = new JSONObject(TOKEN);
                        String nuevotoken = jo.getString("token");
                        new ComprobarLogin(c,urla,codigo1,contraseña1,nuevotoken).execute();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    new ComprobarLogin(c,urla,codigo1,contraseña1,TOKEN).execute();
                }
            }

        }else {
            Toast.makeText(c,"No registrado",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        try {
            JSONArray ja = new JSONArray(s);
            JSONObject jo;
            for (int i=0; i<ja.length();i++){
                jo=ja.getJSONObject(i);
                mensaje = jo.getString("mensaje");
            }
            return 1;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return 0;
    }
}
