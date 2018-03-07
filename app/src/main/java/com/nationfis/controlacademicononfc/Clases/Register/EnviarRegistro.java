package com.nationfis.controlacademicononfc.Clases.Register;

import android.annotation.SuppressLint;
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
 * Created by SamGM on 14/04/2017.
 */

public class EnviarRegistro extends AsyncTask<Void,Void,String> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private ProgressDialog pd;
    @SuppressLint("StaticFieldLeak")
    private String urla,nombre1,apellidop1,apellidom1,correo1,contraseña1,verificacion1,
            fecha1,foto1;
    private Integer documento1,sexo1,codigo1,telefono1,tdocumento1,activo,escuela,sede,lnacimiento;
    public EnviarRegistro(Context c,String urla ,String nombre1, String apellidop1, String apellidom1, String correo1,
                          Integer documento1, Integer sexo1, Integer codigo1, String contraseña1, String verificacion1,
                          Integer telefono1, String fecha1, String foto1, Integer tdocumento1,
                          Integer lnacimiento,Integer activo,Integer escuela,Integer sede) {
        this.c = c;
        this.urla = urla;
        this.nombre1 = nombre1;
        this.apellidop1 = apellidop1;
        this.apellidom1 = apellidom1;
        this.correo1 = correo1;
        this.documento1 = documento1;
        this.sexo1 = sexo1;
        this.foto1 = foto1;
        this.codigo1 = codigo1;
        this.contraseña1 = contraseña1;
        this.verificacion1 = verificacion1;
        this.telefono1 = telefono1;
        this.fecha1 = fecha1;
        this.tdocumento1 = tdocumento1;
        this.lnacimiento = lnacimiento;
        this.activo = activo;
        this.escuela = escuela;
        this.sede = sede;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.registrar();
    }

    private String registrar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if(con==null){
            return null;
        }
        try{
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueRegistro(nombre1,apellidop1,apellidom1,correo1,documento1,sexo1,foto1,codigo1,contraseña1,
                    verificacion1,telefono1,fecha1,tdocumento1,lnacimiento,activo,escuela,sede).packageData());
            bw.flush();
            bw.close();
            os.close();
            int respuesta = con.getResponseCode();
            if(respuesta== HttpURLConnection.HTTP_OK){
                InputStream is =con.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String linea;
                StringBuilder respuestast = new StringBuilder();
                while ((linea=br.readLine())!=null){
                    respuestast.append(linea).append("n");
                }
                return respuestast.toString();
            }else{
                return String.valueOf(respuesta);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Registrando");
        pd.setMessage("Por favor espere un momento");
        pd.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if (s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else{
            AnalizadorRegistro an = new AnalizadorRegistro(c,s,codigo1,contraseña1);
            an.execute();
        }
    }
}
