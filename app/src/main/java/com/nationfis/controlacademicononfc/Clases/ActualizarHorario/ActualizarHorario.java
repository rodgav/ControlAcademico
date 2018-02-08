package com.nationfis.controlacademicononfc.Clases.ActualizarHorario;

import android.app.Dialog;
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

/**
 * Created by GlobalTIC's on 7/02/2018.
 */

public class ActualizarHorario extends AsyncTask<Void, Void, String> {
    private Context c;
    private String urla, accion, codigoa, anioa, sede, dia1, inicio1, fin1, nombredia;
    private Dialog d;
    private TextView dia,inicio,fin;

    public ActualizarHorario(Context c, String urla, String accion, String codigoa,
                             String anioa, String sede, String dia1, String inicio1,
                             String fin1, Dialog d, TextView dia, String nombredia,
                             TextView inicio, TextView fin) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.codigoa = codigoa;
        this.anioa = anioa;
        this.sede = sede;
        this.dia1 = dia1;
        this.inicio1 = inicio1;
        this.fin1 = fin1;
        this.d = d;
        this.dia = dia;
        this.nombredia = nombredia;
        this.inicio = inicio;
        this.fin = fin;
    }


    @Override
    protected String doInBackground(Void... voids) {
        return this.actualizar();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s == null) {
            Toast.makeText(c, "No tiene internet", Toast.LENGTH_SHORT).show();
        } else {
            new AnalizarActualizarHorario(c, s, dia,inicio,fin,nombredia,inicio1,fin1).execute();
        }
    }

    private String actualizar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con == null) {
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueActualizarHorario(accion, codigoa, anioa, sede, dia1, inicio1, fin1).packageData());
            bw.flush();
            bw.close();
            os.close();
            int resp = con.getResponseCode();
            if (resp == HttpURLConnection.HTTP_OK) {
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String linea;
                StringBuffer respuesta = new StringBuffer();
                if (br != null) {
                    while ((linea = br.readLine()) != null) {
                        respuesta.append(linea + "n");
                    }
                } else {
                    return null;
                }
                return respuesta.toString();
            } else {
                return String.valueOf(resp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
