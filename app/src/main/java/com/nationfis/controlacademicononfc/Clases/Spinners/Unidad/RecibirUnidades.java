package com.nationfis.controlacademicononfc.Clases.Spinners.Unidad;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.widget.Spinner;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Clases.Conexion;
import com.nationfis.controlacademicononfc.Clases.Spinners.TipoMatricula.EmpaqueTipoMatricula;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by Sam on 27/05/2017.
 */

public class RecibirUnidades extends AsyncTask<Void, Void, String> {
    private Context c;
    private String urla1,accion;
    private Spinner unidad;
    public RecibirUnidades(Context c, String urla1, Spinner unidad, String accion) {
        this.c = c;
        this.urla1 = urla1;
        this.accion = accion;
        this.unidad = unidad;
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
            new AnalizadorUnidades(c,s,unidad).execute();
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
            bw.write(new EmpaqueUnidades(accion).packageData());
            bw.flush();
            bw.close();
            os.close();
            int resp = con.getResponseCode();
            if (resp==con.HTTP_OK){
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String linea;
                StringBuffer respuesta = new StringBuffer();
                if (br!=null){
                    while ((linea=br.readLine())!=null){
                        respuesta.append(linea+"n");
                    }
                }else {
                    return null;
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
