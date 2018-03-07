package com.nationfis.controlacademicononfc.Clases.ActualizarAsistencia;

import com.kosalgeek.android.md5simply.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/*
 * Created by Sam on 26/04/2017.
 */

class EmpaqueActualizarAsistencia {
    private Integer asistio,codigodoc,codigoasig,codigoest;
    EmpaqueActualizarAsistencia(Integer asistio, Integer codigodoc, Integer codigoasig, Integer codigoest) {
        this.asistio = asistio;
        this.codigodoc = codigodoc;
        this.codigoasig = codigoasig;
        this.codigoest = codigoest;
    }


    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();
        String accion= MD5.encrypt("actualizarasis");
        try {
            jo.put("accion",accion);
            jo.put("1",asistio);
            jo.put("3",codigodoc);
            jo.put("4",codigoasig);
            jo.put("2",codigoest);
            Boolean primero = true;
            Iterator i = jo.keys();
            do {
                String key = i.next().toString();
                String value = jo.get(key).toString();
                if (primero){
                    primero = false;
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
