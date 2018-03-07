package com.nationfis.controlacademicononfc.Clases.EliminarHorario;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Clases.Conexion;
import com.nationfis.controlacademicononfc.Views.MostrarEstudiantes.Estudiantes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/*
 * Created by GlobalTIC's on 7/02/2018.
 */

public class EliminarHorario extends AsyncTask<Void,Void,String> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String urla,accion,inicio,fin,anioa;
    private Dialog d;
    private ArrayList<Estudiantes>estudiantes;
    private int adapterPosition,codigoa,idd,sede;
    public EliminarHorario(Context c, String urla, String accion, Integer codigoa, Integer idd, Integer sede, String inicio, String fin, String anioa, Dialog d, ArrayList<Estudiantes> estudiantes, Integer adapterPosition) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.codigoa = codigoa;
        this.idd = idd;
        this.sede = sede;
        this.inicio = inicio;
        this.fin = fin;
        this.anioa = anioa;
        this.d = d;
        this.estudiantes = estudiantes;
        this.adapterPosition = adapterPosition;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.eliminar();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else {
            new AnalizarEliminarHorario(c,s,estudiantes,adapterPosition,d).execute();
        }
    }

    private String eliminar() {
        //estudiantes.remove(adapterPosition);
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if(con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueEliminarHorario(accion,codigoa,idd,sede,inicio,fin,anioa).packageData());

            bw.flush();
            bw.close();
            os.close();
            int responseCode = con.getResponseCode();
            if(responseCode== HttpURLConnection.HTTP_OK){
                InputStream is = con.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line=br.readLine())!=null){
                    response.append(line).append("n");
                }
                return response.toString();
            }else{
                return String.valueOf(responseCode);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }}
