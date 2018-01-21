package com.nationfis.controlacademicononfc.Clases.EnviarAsistencia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/*
 * Created by SamGM on 23/04/2017.
 */

class EmpacarAsistencia {
    private String coda,codd,fecha,accion;
    EmpacarAsistencia(String coda, String codd, String fecha, String accion) {
        this.coda = coda;
        this.codd = codd;
        this.fecha = fecha;
        this.accion = accion;
    }

    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();
        try {
            jo.put("accion",accion);
            jo.put("coda",coda);
            jo.put("codd",codd);
            jo.put("fecha",fecha);
            Boolean primer = true;
            Iterator i = jo.keys();
            do {
                String key = i.next().toString();
                String value = jo.get(key).toString();
                if (primer){
                    primer = false;
                }else {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(key,"UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(value,"UTF-8"));
            }while (i.hasNext());
            return sb.toString();
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
