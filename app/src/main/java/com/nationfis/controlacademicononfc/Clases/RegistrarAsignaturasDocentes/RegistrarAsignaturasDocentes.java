package com.nationfis.controlacademicononfc.Clases.RegistrarAsignaturasDocentes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
 * Created by Sam on 02/05/2017.
 */

public class RegistrarAsignaturasDocentes extends AsyncTask<Void,Void,String>{
    private ProgressDialog pd;
    private String urla,codio,codigo1,sede,anioa;
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private Dialog d;
    public RegistrarAsignaturasDocentes(Context c, String urla, String codio, String codigo1, String sede, String anioa, Dialog d) {
        this.c = c;
        this.urla = urla;
        this.codigo1 = codigo1;
        this.codio = codio;
        this.sede = sede;
        this.anioa = anioa;
        this.d = d;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Registrando asignatura docente");
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
            new AnalizarRegistroAsignaturaDocentes(c,s,d).execute();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.registrar();
    }

    private String registrar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueAsignaturasDocentes(codio,codigo1,sede,anioa).packageData());
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
