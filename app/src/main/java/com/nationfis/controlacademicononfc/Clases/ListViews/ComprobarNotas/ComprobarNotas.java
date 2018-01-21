package com.nationfis.controlacademicononfc.Clases.ListViews.ComprobarNotas;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.Conexion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Objects;

/*
 * Created by Sam on 06/06/2017.
 */

public class ComprobarNotas extends AsyncTask<Void,Void,String>{
    private ProgressDialog pd;
    @SuppressLint("StaticFieldLeak")
    private Context c;
    @SuppressLint("StaticFieldLeak")
    private ListView notas;
    private String urla,codiuni,codiasi,codival,tipo,codigo;

    public ComprobarNotas(Context c, String urla, ListView notas, String codiuni, String codiasi, String codival, String tipo,String codigo) {
        this.c=c;
        this.urla = urla;
        this.notas = notas;
        this.codiuni = codiuni;
        this.codiasi = codiasi;
        this.codival = codival;
        this.tipo = tipo;
        this.codigo = codigo;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.comprobar();
    }

    private String comprobar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (Objects.equals(tipo,"doc")){
            String accion= MD5.encrypt("mnotas");
            if (con==null){
                return null;
            }
            try {
                OutputStream os = con.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                bw.write(new EmpaqueComprobarNotas1(codiuni,codiasi,codival,accion,codigo).packageData());
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
        }else if (Objects.equals(tipo,"est")){
            String accion= MD5.encrypt("mnotas1");
            if (con==null){
                return null;
            }
            try {
                OutputStream os = con.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                bw.write(new EmpaqueComprobarNotas(codiuni,codiasi,codigo,accion).packageData());
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
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Cargando");
        pd.setMessage("Espere un momento porfavor");
        pd.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if (s==null ){
            Toast.makeText(c,"No tiene internet", Toast.LENGTH_SHORT).show();
        }else {
            new AnalizadorComprobarNotas(c,s,notas,tipo).execute();
        }
    }
}
