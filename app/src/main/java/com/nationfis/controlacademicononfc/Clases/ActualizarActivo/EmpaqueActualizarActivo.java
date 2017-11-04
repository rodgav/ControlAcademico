package com.nationfis.controlacademicononfc.Clases.ActualizarActivo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Sam on 20/08/2017.
 */

public class EmpaqueActualizarActivo {
    private String accion,codigo,activoid,accion1;
    public EmpaqueActualizarActivo(String accion, String accion1, String codigo, String activoid) {
        this.accion = accion;
        this.codigo = codigo;
        this.activoid = activoid;
        this.accion1 = accion1;
    }
    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
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
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
