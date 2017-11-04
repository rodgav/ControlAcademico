package com.nationfis.controlacademicononfc.Clases.ListViews.ComprobarNotas;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Sam on 18/06/2017.
 */

public class EmpaqueComprobarNotas1 {
    private String codiuni,codiasi,codival,accion,codigo;
    public EmpaqueComprobarNotas1(String codiuni, String codiasi, String codival, String accion,String codigo) {
        this.codiuni = codiuni;
        this.codiasi = codiasi;
        this.codival = codival;
        this.accion = accion;
        this.codigo = codigo;
    }

    String packageData() {
        return this.analizar();
    }

    private String analizar() {
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        try {
            jo.put("accion",accion);
            jo.put("1",codiuni);
            jo.put("2",codiasi);
            jo.put("3",codival);
            jo.put("4",codigo);
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
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
