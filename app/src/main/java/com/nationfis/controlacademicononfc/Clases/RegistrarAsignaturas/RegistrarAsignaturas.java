package com.nationfis.controlacademicononfc.Clases.RegistrarAsignaturas;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Clases.Conexion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/*
 * Created by Sam on 02/05/2017.
 */

public class RegistrarAsignaturas extends AsyncTask<Void,Void,String> {
    private ProgressDialog pd;
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String urla,nombre1,semestre;
    public RegistrarAsignaturas(Context c, String urla, String nombre1, String semestre) {
        this.c = c;
        this.urla = urla;
        this.nombre1 = nombre1;
        this.semestre = semestre;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd= new ProgressDialog(c);
        pd.setTitle("Registrando Asignatura");
        pd.setMessage("Por favor espere");
        pd.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if (s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else {
            new AnalizarRegistroAsignaturas(c,s).execute();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.registrar();
    }

    private String registrar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueAsignaturas(nombre1,semestre).packageData());
            bw.flush();
            bw.close();
            os.close();
            int resp = con.getResponseCode();
            if (resp== HttpURLConnection.HTTP_OK){
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String linea;
                StringBuilder respuesta = new StringBuilder();
                while ((linea=br.readLine())!=null){
                    respuesta.append(linea).append("n");
                }
                return respuesta.toString();
            }else {
                return String.valueOf(resp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
