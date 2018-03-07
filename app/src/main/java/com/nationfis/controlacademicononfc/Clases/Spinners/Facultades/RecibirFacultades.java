package com.nationfis.controlacademicononfc.Clases.Spinners.Facultades;

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
 * Created by SamGM on 22/04/2017.
 */

public class RecibirFacultades extends AsyncTask<Void,Void,String> {
    private ProgressDialog pd;
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String urla,matricula1,accion;
    @SuppressLint("StaticFieldLeak")
    private Spinner facultad,escuela,semestre,asignatura;
    //String accion= MD5.encrypt("facultades");
    public RecibirFacultades(Context c, String urla, Spinner facultad, Spinner escuela, Spinner semestre,Spinner asignatura,String matricula1,String accion) {
        this.c = c;
        this.urla = urla;
        this.facultad = facultad;
        this.escuela = escuela;
        this.semestre = semestre;
        this.asignatura = asignatura;
        this.matricula1 = matricula1;
        this.accion = accion;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.recibir();
    }

    private String recibir() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueFacultades(accion).packageData());
            bw.flush();
            bw.close();
            os.close();
            int resp = con.getResponseCode();
            if (resp== HttpURLConnection.HTTP_OK){
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String linea;
                StringBuilder respuesta = new StringBuilder();
                while ((linea = br.readLine())!=null){
                    respuesta.append(linea).append("n");
                }
                br.close();
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
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Cargando Datos");
        pd.setMessage("Por favor espere un momento");
        pd.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if (s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else{
            AnalizadorFacultades an = new AnalizadorFacultades(c,s,facultad,escuela,semestre,asignatura,matricula1);
            an.execute();
        }
    }
}
