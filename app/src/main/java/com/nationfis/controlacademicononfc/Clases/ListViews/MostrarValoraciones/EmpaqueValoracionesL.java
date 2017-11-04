package com.nationfis.controlacademicononfc.Clases.ListViews.MostrarValoraciones;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Sam on 18/06/2017.
 */

public class EmpaqueValoracionesL {
    private String codiasi,codiuni,accion,codigo;
    public EmpaqueValoracionesL(String codiasi, String codiuni, String accion,String codigo) {
        this.codiasi = codiasi;
        this.codiuni = codiuni;
        this.accion = accion;
        this.codigo = codigo;
    }

    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        try {
            jo.put("accion",accion);
            jo.put("1",codiasi);
            jo.put("2",codiuni);
            jo.put("3",codigo);
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
