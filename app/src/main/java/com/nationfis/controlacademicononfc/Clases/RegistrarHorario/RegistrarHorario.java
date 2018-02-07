package com.nationfis.controlacademicononfc.Clases.RegistrarHorario;

/*
 * Created by GlobalTIC's on 5/02/2018.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Clases.Conexion;
import com.nationfis.controlacademicononfc.Clases.Spinners.Semana.AnalizadorSemana;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class RegistrarHorario extends AsyncTask<Void,Void,String> {
    private ProgressDialog pd;
    private Context c;
    private String urla,accioni1,asignatura,dia1,sede,inicio1,fin1,anio;
    public RegistrarHorario(Context c, String urla, String accioni1, String asignatura,
                            String dia1, String sede, String inicio1, String fin1, String anio) {
        this.c = c;
        this.urla = urla;
        this.accioni1 = accioni1;
        this.asignatura = asignatura;
        this.dia1 = dia1;
        this.sede = sede;
        this.inicio1 = inicio1;
        this.fin1 = fin1;
        this.anio = anio;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.registrar();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Registrando horario");
        pd.setMessage("Por favor espere");
        pd.show();
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if (s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else{
            new AnalizarRegistrarHorario(c,s).execute();
        }
    }

    private String registrar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueRegistrarHorario(accioni1,asignatura,dia1,sede,inicio1,fin1,anio).packageData());
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
