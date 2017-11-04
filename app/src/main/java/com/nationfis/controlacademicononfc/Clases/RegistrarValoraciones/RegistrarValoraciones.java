package com.nationfis.controlacademicononfc.Clases.RegistrarValoraciones;

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

/**
 * Created by Sam on 28/05/2017.
 */

public class RegistrarValoraciones extends AsyncTask<Void,Void,String> {
    private ProgressDialog pd;
    private Context c;
    private String urla,nombre1,peso1,unidad1,codigoasi1,accion,codigo;
    public RegistrarValoraciones(Context c, String urla, String nombre1, String peso1, String unidad1, String codigoasi1, String accion,String codigo) {
        this.c = c;
        this.urla = urla;
        this.nombre1 = nombre1;
        this.peso1 = peso1;
        this.unidad1 = unidad1;
        this.codigoasi1 = codigoasi1;
        this.accion = accion;
        this.codigo = codigo;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.registrar();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Registrando");
        pd.setMessage("Por favor espere");
        pd.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if (s==null){
            Toast.makeText(c,"No tiene Interner",Toast.LENGTH_SHORT).show();
        }else{
            new AnalizadorRegistroValoraciones(c,s).execute();
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
            bw.write(new EmpaqueValoraciones(nombre1,peso1,unidad1,codigoasi1,accion,codigo).packageData());
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
