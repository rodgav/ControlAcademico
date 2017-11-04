package com.nationfis.controlacademicononfc.Clases.Spinners.TipoMatricula;

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


/**
 * Created by SamGM on 23/04/2017.
 */

public class RecibirTipoMatricula extends AsyncTask<Void,Void,String> {
    private Context c;
    private ProgressDialog pd;
    private String urla1,accion;
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
