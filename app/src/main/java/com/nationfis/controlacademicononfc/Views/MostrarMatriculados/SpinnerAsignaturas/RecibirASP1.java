package com.nationfis.controlacademicononfc.Views.MostrarMatriculados.SpinnerAsignaturas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
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
 * Created by GlobalTIC's on 7/03/2018.
 */

public class RecibirASP1 extends AsyncTask<Void,Void,String> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String urla;
    private Integer codigo;
    @SuppressLint("StaticFieldLeak")
    private Spinner asignaturas;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView estudiantes,estudiantesa;
    public RecibirASP1(Context c, String urla, Integer codigo, Spinner asignaturas, RecyclerView estudiantes, RecyclerView estudiantesa) {
        this.c = c;
        this.urla = urla;
        this.codigo = codigo;
        this.asignaturas = asignaturas;
        this.estudiantes = estudiantes;
        this.estudiantesa = estudiantesa;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s == null) {
            Toast.makeText(c, "No tiene internet", Toast.LENGTH_SHORT).show();
        } else {
            new AnalizadorASP1(c, s, asignaturas,estudiantes,estudiantesa).execute();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.recibir();
    }

    private String recibir() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con == null) {
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueASP1(codigo).packageData());
            bw.flush();
            bw.close();
            os.close();
            int resp = con.getResponseCode();
            if (resp == HttpURLConnection.HTTP_OK) {
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String linea;
                StringBuilder respuesta = new StringBuilder();
                while ((linea = br.readLine()) != null) {
                    respuesta.append(linea).append("n");
                }
                return respuesta.toString();
            } else {
                return String.valueOf(resp);
            }

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}

