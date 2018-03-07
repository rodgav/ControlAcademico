package com.nationfis.controlacademicononfc.Clases.Reportes.Asistenciapdf;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/*
 * Created by GlobalTIC's on 5/11/2017.
 */

class EmpaqueAsistenciaPDF {
    private String accion,fecha;
    private Integer asig,ep;
    EmpaqueAsistenciaPDF(String accion, Integer asig, String fecha,Integer ep) {
        this.accion = accion;
        this.asig = asig;
        this.fecha = fecha;
        this.ep = ep;
    }
    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();

        try {
            jo.put("accion",accion);
            jo.put("1",asig);
            jo.put("2",fecha);
            jo.put("3",ep);
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
