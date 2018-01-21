package com.nationfis.controlacademicononfc.Clases.ActualizarAsistencia;

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
 * Created by Sam on 26/04/2017.
 */

public class ActualizarAsistencia extends AsyncTask<Void,Void,String> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private ProgressDialog pd;
    private Dialog d;
    @SuppressLint("StaticFieldLeak")
    private TextView asisti;
    private String urla,asistio,codigodoc,codigoasig,codigoest,fecha;
    public ActualizarAsistencia(Context c, String urla, String asistio, String codigodoc, String codigoasig, String codigoest,
                                String fecha, Dialog d, TextView asisti) {
        this.c = c;
        this.urla = urla;
        this.asistio = asistio;
        this.codigodoc = codigodoc;
        this.codigoasig = codigoasig;
        this.codigoest = codigoest;
        this.fecha = fecha;
        this.d = d;
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
            AnalizarComprobarAsistencia analizarComprobarAsistencia = new AnalizarComprobarAsistencia(c,s,d,asisti,asistio);
            analizarComprobarAsistencia.execute();
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
            bw.write(new EmpaqueActualizarAsistencia(asistio,codigodoc,codigoasig,codigoest,fecha).packageData());
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
