package com.nationfis.controlacademicononfc.Clases.Spinners.TipoMatricula;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
 * Created by SamGM on 23/04/2017.
 */

public class RecibirTipoMatricula extends AsyncTask<Void,Void,String> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private ProgressDialog pd;
    private String urla1,accion;
    @SuppressLint("StaticFieldLeak")
    private Spinner tipom;
    public RecibirTipoMatricula(Context c, String urla1, Spinner tipom,String accion) {
        this.c = c;
        this.urla1 = urla1;
        this.tipom = tipom;
        this.accion = accion;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Cargando datos");
        pd.setMessage("Por favor espere un momento");
        pd.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if (s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else {
            new AnalizadorTipoMatricula(c,s,tipom).execute();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.recibir();
    }

    private String recibir() {
        HttpURLConnection con = Conexion.httpURLConnection(urla1);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueTipoMatricula(accion).packageData());
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
