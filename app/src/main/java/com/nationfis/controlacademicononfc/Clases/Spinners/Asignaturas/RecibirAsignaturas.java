package com.nationfis.controlacademicononfc.Clases.Spinners.Asignaturas;

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
 * Created by SamGM on 22/04/2017.
 */

public class RecibirAsignaturas extends AsyncTask<Void,Void,String> {
    private Context c;
    private String urla,s1;
    private Spinner asignatura;
    public RecibirAsignaturas(Context c, String urla, String s, Spinner asignatura) {
        this.c = c;
        this.urla = urla;
        this.s1 = s;
        this.asignatura = asignatura;

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
            AnalizadorAsignaturas a = new AnalizadorAsignaturas(c,s,asignatura);
            a.execute();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.recibir();
    }

    private String recibir() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueAsignaturas(s1).packageData());
            bw.flush();
            bw.close();
            os.close();
            int resp = con.getResponseCode();
            if (resp==con.HTTP_OK){
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String linea;
                StringBuffer repuesta = new StringBuffer();
                if (br!=null){
                    while((linea=br.readLine())!=null){
                        repuesta.append(linea+"n");
                    }
                }else {
                    return null;
                }
                return repuesta.toString();
            }else {
                return String.valueOf(resp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }
}
