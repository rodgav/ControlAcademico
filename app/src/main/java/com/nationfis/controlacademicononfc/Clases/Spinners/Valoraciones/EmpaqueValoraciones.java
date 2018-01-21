package com.nationfis.controlacademicononfc.Clases.Spinners.Valoraciones;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/*
 * Created by Sam on 28/05/2017.
 */

class EmpaqueValoraciones {
    private String codiasi,codiuni,accion,codigo;
    EmpaqueValoraciones(String codiasi, String codiuni, String accion, String codigo) {
        this.accion = accion;
        this.codiasi = codiasi;
        this.codiuni = codiuni;
        this.codigo = codigo;
    }

    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();
        try {
            jo.put("accion",accion);
            jo.put("1",codiasi);
            jo.put("2",codiuni);
            jo.put("3",codigo);
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
