package com.nationfis.controlacademicononfc.Clases.ActualizarActivo;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
 * Created by Sam on 20/08/2017.
 */

public class ActualizarActivo extends AsyncTask<Void,Void,String> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private Dialog d;
    private String urla,accion,activoid,codigo,accion1;
    @SuppressLint("StaticFieldLeak")
    private TextView activado;
    private ProgressDialog pd;
    public ActualizarActivo(Context c, String urla, String accion,String accion1, String activoid, String codigo, Dialog d, TextView activado) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.activoid = activoid;
        this.codigo = codigo;
        this.d = d;
        this.activado = activado;
        this.accion1 = accion1;
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
            new AnalizarActualizarActivo(c,s,d,activado,activoid).execute();
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
            bw.write(new EmpaqueActualizarActivo(accion,accion1,codigo,activoid).packageData());
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
