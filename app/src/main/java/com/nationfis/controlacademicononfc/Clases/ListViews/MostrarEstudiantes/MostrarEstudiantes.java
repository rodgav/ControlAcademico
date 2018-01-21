package com.nationfis.controlacademicononfc.Clases.ListViews.MostrarEstudiantes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
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
 * Created by Sam on 20/08/2017.
 */

public class MostrarEstudiantes extends AsyncTask<Void,Void,String> {
    private String urla,accion,s1,ep,anioa,sede;
    @SuppressLint("StaticFieldLeak")
    private Context c;
    @SuppressLint("StaticFieldLeak")
    private ListView estudiantes;
    @SuppressLint("StaticFieldLeak")
    private SwipeRefreshLayout swipeRefreshLayout;
    public MostrarEstudiantes(Context c, String urla, String accion, String s1, ListView estudiantes, String ep, String anioa, String sede, SwipeRefreshLayout swipeRefreshLayout) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.s1 = s1;
        this.estudiantes = estudiantes;
        this.ep = ep;
        this.anioa = anioa;
        this.sede = sede;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.mostrar();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }else{
            new AnalizarEstudiantes(c,s,estudiantes,accion,swipeRefreshLayout).execute();
        }
    }

    private String mostrar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueEstudiantes(accion,s1,ep,anioa,sede).packageData());
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
