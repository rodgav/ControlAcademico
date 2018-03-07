package com.nationfis.controlacademicononfc.Clases.Spinners.Escuelas;

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
 * Created by SamGM on 22/04/2017.
 */

public class RecibirEscuelas extends AsyncTask<Void,Void,String> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String urla,matricula1;
    private Integer s0;
    @SuppressLint("StaticFieldLeak")
    private Spinner escuela,semestre,asignatura;
    public RecibirEscuelas(Context c, String urla, Integer s0, Spinner escuela, Spinner semestre,Spinner asignatura,String matricula1) {
        this.c = c;
        this.urla = urla;
        this.s0 = s0;
        this.escuela = escuela;
        this.semestre = semestre;
        this.matricula1 = matricula1;
        this.asignatura = asignatura;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else {
            AnalizadorEscuelas a = new AnalizadorEscuelas(c,escuela,semestre,asignatura,s,matricula1);
            a.execute();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.recibir();
    }

    private String recibir() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con == null){
            return null;
        }
        try {

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueEscuelas(s0).packageData());
            bw.flush();
            bw.close();
            os.close();
            int respuesta = con.getResponseCode();
            if (respuesta== HttpURLConnection.HTTP_OK){
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String linea;
                StringBuilder respuest = new StringBuilder();
                while((linea=br.readLine())!=null){
                    respuest.append(linea).append("n");
                }
                return respuest.toString();
            }else {
                return String.valueOf(respuesta);
            }

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }

}
