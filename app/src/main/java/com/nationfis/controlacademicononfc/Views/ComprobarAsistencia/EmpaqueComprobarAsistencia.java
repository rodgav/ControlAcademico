package com.nationfis.controlacademicononfc.Views.ComprobarAsistencia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Sam on 25/04/2017.
 */

class EmpaqueComprobarAsistencia {
    private String s,fecha,accion;
    EmpaqueComprobarAsistencia(String s, String fecha, String accion) {
        this.s=s;
        this.fecha= fecha;
        this.accion = accion;
    }

    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();
        try {
            jo.put("accion",accion);
            jo.put("s",s);
            jo.put("fecha",fecha);
            Iterator i = jo.keys();
            Boolean primer = true;
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
