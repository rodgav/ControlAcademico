package com.nationfis.controlacademicononfc.Clases.ListViews.MostrarAsignaturas;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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

/**
 * Created by Sam on 18/06/2017.
 */

public class MostrarAsignaturas extends AsyncTask<Void,Void,String> {
    private Context c;
    private ProgressDialog pd;
    private String urla,accion,codigo,anioa;
    private ListView estudiantes;
    public MostrarAsignaturas(Context c, String urla, String accion, String codigo, ListView estudiantes,String anioa) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.codigo = codigo;
        this.estudiantes = estudiantes;
        this.anioa = anioa;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Cargando");
        pd.setMessage("Espere un momento");
        pd.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if (s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else{
            new AnalizarAsignaturas(c,s,estudiantes).execute();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.mostrar();
    }

    private String mostrar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueMostrarAsignaturas(codigo,accion,anioa).packageData());
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
