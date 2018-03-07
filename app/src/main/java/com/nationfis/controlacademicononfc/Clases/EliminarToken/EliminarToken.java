package com.nationfis.controlacademicononfc.Clases.EliminarToken;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Clases.Conexion;
import com.nationfis.controlacademicononfc.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;


/*
 * Created by GlobalTIC's on 31/01/2018.
 */

public class EliminarToken extends AsyncTask<Void,Void,String> {
    private ProgressDialog pd;
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String urla,accion,token;
    private Integer codigo;
    public EliminarToken(Context c, String urla, String accion, Integer codigo, String token) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.codigo = codigo;
        this.token = token;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.eliminar();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c,android.R.style.Theme_Translucent_NoTitleBar);
        pd.show();
        pd.setContentView(R.layout.login);
        TextView titulo = pd.findViewById(R.id.titulo);
        String i = "Cerrando Sesion";
        titulo.setText(i);
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if(s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else {
            new AnalizarEliminarToken(c,s).execute();
        }
    }

    private String eliminar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if(con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueEliminarToken(accion,codigo,token).packageData());

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
    }
}
