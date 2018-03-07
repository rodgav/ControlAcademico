package com.nationfis.controlacademicononfc.Clases.ActualizarAsistencia;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
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
 * Created by Sam on 26/04/2017.
 */

public class ActualizarAsistencia extends AsyncTask<Void,Void,String> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private ProgressDialog pd;
    @SuppressLint("StaticFieldLeak")
    private TextView asisti;
    private String urla;
    private Integer asistio,codigodoc,codigoasig,codigoest;
    public ActualizarAsistencia(Context c, String urla, Integer asistio, Integer codigodoc, Integer codigoasig, Integer codigoest, TextView asisti) {
        this.c = c;
        this.urla = urla;
        this.asistio = asistio;
        this.codigodoc = codigodoc;
        this.codigoasig = codigoasig;
        this.codigoest = codigoest;
        this.asisti = asisti;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.actualizar();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Enviando");
        pd.setCancelable(false);
        pd.setMessage("Espere porfavor");
        pd.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if (s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else {
            new AnalizarComprobarAsistencia(c,s,asisti,asistio).execute();
        }
    }

    private String actualizar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueActualizarAsistencia(asistio,codigodoc,codigoasig,codigoest).packageData());
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
