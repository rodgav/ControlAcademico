package com.nationfis.controlacademicononfc.Views.ComprobarAsistencia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
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
 * Created by Sam on 25/04/2017.
 */

public class ComprobarAsistencia extends AsyncTask<Void,Void,String>{
    //private ProgressDialog pd;
    private String urla2,fecha,tipo,accion;
    private Integer s,a;
    @SuppressLint("StaticFieldLeak")
    private Context c;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView estudiantes;
    @SuppressLint("StaticFieldLeak")
    private SwipeRefreshLayout swipeRefreshLayout;
    public ComprobarAsistencia(Context c, String urla2, Integer s, String fecha, RecyclerView estudiantes,
                               String tipo, String accion, SwipeRefreshLayout swipeRefreshLayout,Integer a) {
        this.c = c;
        this.urla2 = urla2;
        this.s = s;
        this.estudiantes = estudiantes;
        this.fecha = fecha;
        this.tipo = tipo;
        this.accion = accion;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.a = a;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.comprobar();
    }

    private String comprobar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla2);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueComprobarAsistencia(s,fecha,accion).packageData());
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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //pd.dismiss();
        swipeRefreshLayout.setRefreshing(false);
        if (s==null ){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else {
            new AnalizadorComprobarAsistencia(c,s,estudiantes,tipo,a).execute();
        }
    }
}
