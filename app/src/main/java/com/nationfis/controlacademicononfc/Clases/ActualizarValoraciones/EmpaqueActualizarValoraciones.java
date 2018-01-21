package com.nationfis.controlacademicononfc.Clases.ActualizarValoraciones;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/*
 * Created by Sam on 18/06/2017.
 */

class EmpaqueActualizarValoraciones {
    private String accion,codasi,coduni,codval,codigo,peso2;
    EmpaqueActualizarValoraciones(String accion, String codasi, String coduni, String codval, String codigo, String peso2) {
        this.accion = accion;
        this.codasi = codasi;
        this.coduni = coduni;
        this.codval = codval;
        this.codigo = codigo;
        this.peso2 = peso2;
    }
    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();

        try {
            jo.put("accion",accion);
            jo.put("1",codasi);
            jo.put("2",coduni);
            jo.put("3",codval);
            jo.put("4",codigo);
            jo.put("5",peso2);
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
