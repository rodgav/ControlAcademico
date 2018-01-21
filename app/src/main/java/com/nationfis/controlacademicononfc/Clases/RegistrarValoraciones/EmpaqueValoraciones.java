package com.nationfis.controlacademicononfc.Clases.RegistrarValoraciones;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/*
 * Created by Sam on 28/05/2017.
 */

class EmpaqueValoraciones {
    private String nombre1,peso1,unidad1,codigoasi1,accion,codigo;
    EmpaqueValoraciones(String nombre1, String peso1, String unidad1, String codigoasi1, String accion, String codigo) {
        this.nombre1 = nombre1;
        this.peso1 = peso1;
        this.unidad1 = unidad1;
        this.codigoasi1 = codigoasi1;
        this.accion = accion;
        this.codigo = codigo;
    }

    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        try {
            jo.put("accion",accion);
            jo.put("1",nombre1);
            jo.put("2",peso1);
            jo.put("3",unidad1);
            jo.put("4",codigo);
            jo.put("5",codigoasi1);
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
