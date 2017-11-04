package com.nationfis.controlacademicononfc.Clases.ListViews.MostrarValoraciones;

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

public class MostrarValoracionesL extends AsyncTask<Void,Void,String> {
    private Context c;
    private String urla,accion,codiasi,codiuni,codigo;
    private ListView valores;
    private ProgressDialog pd;
    public MostrarValoracionesL(Context c, String urla, String accion, String codiasi, String codiuni, ListView valores,String codigo) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.codiasi = codiasi;
        this.codiuni = codiuni;
        this.valores = valores;
        this.codigo = codigo;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Cargando");
        pd.setMessage("Porfavor espere");
        pd.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if (s==null ){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else {
            new AnalizarValoracionesL(c,s,valores).execute();
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
            bw.write(new EmpaqueValoracionesL(codiasi,codiuni,accion,codigo).packageData());
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
