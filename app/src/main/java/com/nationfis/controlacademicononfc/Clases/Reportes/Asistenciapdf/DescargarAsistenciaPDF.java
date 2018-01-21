package com.nationfis.controlacademicononfc.Clases.Reportes.Asistenciapdf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.BuildConfig;
import com.nationfis.controlacademicononfc.Clases.Conexion;
import com.nationfis.controlacademicononfc.Activitys.NavigationActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by GlobalTIC's on 5/11/2017.
 */

public class DescargarAsistenciaPDF extends AsyncTask<String,Integer,String> {
    private String urla1, asig, fecha;
    private Context c;
    private PowerManager.WakeLock mWakeLock;
    private ProgressDialog mProgressDialog;

    public DescargarAsistenciaPDF(Context c, String urla1, String asig, String fecha) {
        this.urla1 = urla1;
        this.asig = asig;
        this.fecha = fecha;
        this.c = c;
    }
    @Override
    protected String doInBackground(String... strings) {
        return this.descargar();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(c);
        mProgressDialog.setTitle("Descargando pdf");
        mProgressDialog.setMessage("Por favor espere");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
        PowerManager pm = (PowerManager) c.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mWakeLock.release();
        mProgressDialog.dismiss();
        if (s != null) {
            Toast.makeText(c, "Error en la descarga" + s, Toast.LENGTH_LONG).show();
        }else {
            //File pdfFile = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + "maven.pdf");
            @SuppressLint("SdCardPath") File pdfFile = new File("/sdcard/"+asig+fecha+".pdf");
            //Uri path = Uri.fromFile(pdfFile);
            Uri path = FileProvider.getUriForFile(c, BuildConfig.APPLICATION_ID+".provider",pdfFile);
            //Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            Intent pdfIntent =  new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                c.startActivity(pdfIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(c, "No tiene un visualizador pdf", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(values[0]);
    }

    @SuppressLint("SdCardPath")
    private String descargar() {
        InputStream input = null;
        OutputStream output = null;

        HttpURLConnection con = Conexion.httpURLConnection(urla1);

        if (con == null) {
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueAsistenciaPDF(asig, fecha).packageData());
            bw.flush();
            bw.close();
            os.close();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + con.getResponseCode()
                        + " " + con.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = con.getContentLength();

            // download the file
            input = con.getInputStream();
            output = new FileOutputStream("/sdcard/"+asig+fecha+".pdf");

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }
            if (con != null)
                con.disconnect();
        }
        return null;
    }
}

