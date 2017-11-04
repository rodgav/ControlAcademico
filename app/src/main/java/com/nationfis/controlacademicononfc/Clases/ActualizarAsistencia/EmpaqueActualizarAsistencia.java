package com.nationfis.controlacademicononfc.Clases.ActualizarAsistencia;

import com.kosalgeek.android.md5simply.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Sam on 26/04/2017.
 */

public class EmpaqueActualizarAsistencia {
    private String asistio,codigodoc,codigoasig,codigoest,fecha;

    public EmpaqueActualizarAsistencia(String asistio, String codigodoc, String codigoasig, String codigoest,String fecha) {
        this.asistio = asistio;
        this.codigodoc = codigodoc;
        this.codigoasig = codigoasig;
        this.codigoest = codigoest;
        this.fecha = fecha;
    }


    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        String accion= MD5.encrypt("actualizarasis");
        try {
            jo.put("accion",accion);
            jo.put("1",asistio);
            jo.put("3",codigodoc);
            jo.put("4",codigoasig);
            jo.put("2",codigoest);
            jo.put("5",fecha);
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
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
