package com.nationfis.controlacademicononfc.Clases.RegistrarMatricula;

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
 * Created by SamGM on 22/04/2017.
 */

public class RecibirMatricula extends AsyncTask<Void,Void,String> {
    private ProgressDialog pd;
    private Context c;
    private String urla,baucher1,codigo1,matricula1,anio,escuela,an,tipom,activo,sede;
    public RecibirMatricula(Context c, String urla, String baucher1, String codigo1, String matricula1,
                            String anio,String escuela,String an,String tipom,String activo,String sede) {
        this.c = c;
        this.urla = urla;
        this.baucher1 = baucher1;
        this.codigo1 = codigo1;
        this.matricula1 = matricula1;
        this.anio = anio;
        this.escuela = escuela;
        this.an = an;
        this.tipom = tipom;
        this.activo = activo;
        this.sede = sede;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.parse();
    }

    private String parse() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if(con==null){
            return null;
        }
        try{
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueMatricula(baucher1,codigo1,matricula1,anio,escuela,an,tipom,activo,sede).packageData());
            bw.flush();
            bw.close();
            os.close();
            int respuesta = con.getResponseCode();
            if (respuesta==con.HTTP_OK){
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String linea;
                StringBuffer respuestat = new StringBuffer();
                if (br!=null){
                    while ((linea=br.readLine())!=null){
                        respuestat.append(linea+"n");
                    }
                }else {
                    return  null;
                }
                return respuestat.toString();
            }else {
                return String.valueOf(respuesta);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(c);
        pd.setTitle("Registrando Matricula");
        pd.setMessage("Por favor espere un momento");
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if (s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else {
            AnalizadorMatricula an = new AnalizadorMatricula(c,s);
            an.execute();
        }
    }
}
