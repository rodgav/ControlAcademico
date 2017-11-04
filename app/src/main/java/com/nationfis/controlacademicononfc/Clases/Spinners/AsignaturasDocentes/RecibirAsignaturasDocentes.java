package com.nationfis.controlacademicononfc.Clases.Spinners.AsignaturasDocentes;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
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

public class RecibirAsignaturasDocentes extends AsyncTask<Void,Void,String>{
    private ProgressDialog pd;
    private Context c;
    private String urla,codigo,tipo,sede,anioa;
    private Spinner asignaturas;
    private ListView estudiantes,estudiantesa;
    public RecibirAsignaturasDocentes(Context c, String urla, String codigo, Spinner asignaturas, ListView estudiantes,
                                      ListView estudiantesa,String tipo,String sede, String anioa) {
        this.c = c;
        this.urla = urla;
        this.codigo = codigo;
        this.asignaturas = asignaturas;
        this.estudiantes = estudiantes;
        this.estudiantesa = estudiantesa;
        this.tipo = tipo;
        this.sede = sede;
        this.anioa = anioa;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Cargando datos");
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
            AnalizadorAsignaturasDocentes a = new AnalizadorAsignaturasDocentes(c,s,asignaturas,estudiantes,estudiantesa,tipo,sede,anioa);
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
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueAsignaturasDocentes(codigo).packageData());
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
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

}
