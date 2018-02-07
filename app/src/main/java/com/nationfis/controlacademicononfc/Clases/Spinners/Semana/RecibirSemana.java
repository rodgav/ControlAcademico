package com.nationfis.controlacademicononfc.Clases.Spinners.Semana;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Spinner;
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
 * Created by GlobalTIC's on 5/02/2018.
 */

public class RecibirSemana extends AsyncTask<Void,Void,String> {
    private String urla,accion;
    @SuppressLint("StaticFieldLeak")
    private Spinner semana;
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private int posicion;

    public RecibirSemana(String urla, String accion, Spinner semana, Context c,int posicion) {
        this.urla = urla;
        this.accion = accion;
        this.semana = semana;
        this.c = c;
        this.posicion = posicion;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else {
            new AnalizadorSemana(c,s,semana,posicion).execute();
        }
    }
    @Override
    protected String doInBackground(Void... voids) {
        return this.recibir();
    }
    private String recibir() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueSemana(accion).packageData());
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
