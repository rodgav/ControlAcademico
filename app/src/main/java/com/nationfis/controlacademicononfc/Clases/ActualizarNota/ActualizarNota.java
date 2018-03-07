package com.nationfis.controlacademicononfc.Clases.ActualizarNota;

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
 * Created by Sam on 06/06/2017.
 */

public class ActualizarNota extends AsyncTask<Void,Void,String>{
    private String urla,accion,anioa;
    private ProgressDialog pd;
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private Dialog d;
    @SuppressLint("StaticFieldLeak")
    private TextView nota;
    private Integer nota2,codasi,coduni,codigo2,codigo3;
    public ActualizarNota(Context c, String urla, String accion, Integer nota2,
                          Integer codasi, Integer coduni, Integer codigo2, Dialog d, TextView nota,Integer codigo3,String anioa) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.nota2 = nota2;
        this.codasi = codasi;
        this.coduni = coduni;
        this.codigo2 = codigo2;
        this.d = d;
        this.nota = nota;
        this.codigo3 = codigo3;
        this.anioa = anioa;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.actualizar();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Cargando");
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
            AnalizarComprobarNota analizarComprobarNota = new AnalizarComprobarNota(c,s,d,nota,nota2);
            analizarComprobarNota.execute();
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
            bw.write(new EmpaqueActualizarNota(accion,nota2,codasi,coduni,codigo2,codigo3,anioa).packageData());
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
