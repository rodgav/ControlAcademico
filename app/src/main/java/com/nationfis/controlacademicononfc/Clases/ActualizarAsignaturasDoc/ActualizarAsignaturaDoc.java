package com.nationfis.controlacademicononfc.Clases.ActualizarAsignaturasDoc;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
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
 * Created by GlobalTIC's on 7/02/2018.
 */

public class ActualizarAsignaturaDoc extends AsyncTask<Void,Void,String> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String urla,accion,anioa,nombrea;
    private Dialog d;
    @SuppressLint("StaticFieldLeak")
    private TextView asignatura;
    private Integer codigo,sede,codigoa,codigoaant;
    public ActualizarAsignaturaDoc(Context c, String urla, String accion, Integer codigo, String anioa, Integer sede, Integer codigoa, Dialog d, TextView asignatura, String nombrea, Integer codigoaant) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.codigo = codigo;
        this.anioa = anioa;
        this.sede = sede;
        this.codigoa = codigoa;
        this.d = d;
        this.asignatura = asignatura;
        this.nombrea = nombrea;
        this.codigoaant = codigoaant;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.actualizar();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else {
            new AnalizarActualizarAsignaturaDoc(c,s,asignatura,nombrea).execute();
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
            bw.write(new EmpaqueActualizarAsignaturaDoc(accion,codigo,anioa,sede,codigoa,codigoaant).packageData());
            bw.flush();
            bw.close();
            os.close();
            int resp = con.getResponseCode();
            if (resp==HttpURLConnection.HTTP_OK){
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
