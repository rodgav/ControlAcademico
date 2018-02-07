package com.nationfis.controlacademicononfc.Clases.ActualizarNota;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/*
 * Created by Sam on 06/06/2017.
 */

class EmpaqueActualizarNota {
    private String accion,nota2,codasi,coduni,codigo2,codigo3;
    EmpaqueActualizarNota(String accion, String nota2, String codasi, String coduni, String codigo2, String codigo3) {
        this.accion = accion;
        this.nota2 = nota2;
        this.codasi = codasi;
        this.coduni = coduni;
        this.codigo2 = codigo2;
        this.codigo3 = codigo3;
    }

    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();

        try {
            jo.put("accion",accion);
            jo.put("1",coduni);
            jo.put("2",codasi);
            jo.put("3",codigo2);
            jo.put("5",nota2);
            jo.put("6",codigo3);
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
