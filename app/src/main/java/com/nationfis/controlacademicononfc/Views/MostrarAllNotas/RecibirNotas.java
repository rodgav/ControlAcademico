package com.nationfis.controlacademicononfc.Views.MostrarAllNotas;

/*
 * Created by GlobalTIC's on 25/02/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TableLayout;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Clases.Conexion;
import com.nationfis.controlacademicononfc.Views.MostrarAllNotas.SpinnerAsignaturas.AnalizadorASP;
import com.nationfis.controlacademicononfc.Views.MostrarHorarios.AnalizarHorarios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class RecibirNotas extends AsyncTask<Void, Void, String> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String urla,accion,anioa;
    private Integer codigo;
    @SuppressLint("StaticFieldLeak")
    private TableLayout notas;

    public RecibirNotas(Context c, String urla, String accion, String anioa, Integer codigo, TableLayout notas) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.anioa = anioa;
        this.codigo = codigo;
        this.notas = notas;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.recibir();
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else{
            new AnalizarNotas(c,s,notas).execute();
        }
    }
    private String recibir() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueNotas(accion,anioa,codigo).packageData());
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
