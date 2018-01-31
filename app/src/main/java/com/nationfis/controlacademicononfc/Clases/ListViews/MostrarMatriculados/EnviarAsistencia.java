package com.nationfis.controlacademicononfc.Clases.ListViews.MostrarMatriculados;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
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

public class EnviarAsistencia extends AsyncTask<Void,Void,String> {
    private ProgressDialog pd;
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String urla,s,accion,sede,anioa;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView estudiantes;
    public EnviarAsistencia(Context c, String urla, String s, RecyclerView estudiantes, String accion, String sede, String anioa) {
        this.c = c;
        this.urla = urla;
        this.s = s;
        this.estudiantes = estudiantes;
        this.accion = accion;
        this.sede = sede;
        this.anioa = anioa;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.enviar();
    }

    private String enviar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueAsistencia(s,accion,sede,anioa).packageData());
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
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Cargando Estudiantes");
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
            new AnalizadorAsistencia(c,s,estudiantes).execute();
        }
    }
}
