package com.nationfis.controlacademicononfc.Clases.EnviarNotas;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/*
 * Created by Sam on 29/05/2017.
 */

class EmpacarNota {
    private String accion,anioa;
    private Integer codiasi,codiuni,codigo;
    EmpacarNota(Integer codiasi, Integer codiuni, String accion, Integer codigo, String anioa) {
        this.codiasi = codiasi;
        this.codiuni = codiuni;
        this.accion = accion;
        this.codigo = codigo;
        this.anioa = anioa;
    }

    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();
        try {
            jo.put("accion",accion);
            jo.put("1",codiasi);
            jo.put("2",codiuni);
            jo.put("4",codigo);
            jo.put("5",anioa);
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
