package com.nationfis.controlacademicononfc.Clases.ActualizarActivo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/*
 * Created by Sam on 20/08/2017.
 */

class EmpaqueActualizarActivo {
    private String accion,accion1;
    private Integer codigo,activoid;
    EmpaqueActualizarActivo(String accion, String accion1, Integer codigo, Integer activoid) {
        this.accion = accion;
        this.codigo = codigo;
        this.activoid = activoid;
        this.accion1 = accion1;
    }
    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();
        try {
            jo.put("accion",accion);
            jo.put("1",codigo);
            jo.put("2",activoid);
            jo.put("3",accion1);
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
